package cama.api.webclient;

import cama.api.auth.OplAccessToken;
import cama.api.config.LDTSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class OplSandboxGeofencingClient extends WebClientBase {
    private static final String AUTHORIZATION = "Authorization";

    private final WebClient.Builder builder;
    private final OplWebClientProperties webClientProperties;
    private OplAccessToken accessToken;

    public <T, V> T post(String path, V body, String xCorrelator, Class<T> clazz) {
        log.info("OPL SANDBOX POST: Connecting to external service, with request body: {}, xCorrelator: {}", body, xCorrelator);
        WebClient webClient = builder.filter(logRequest()).build();
        String bodyStr = convertBody(body);
        return obtainAccessToken().flatMap(token ->
                webClient.post()
                        .uri(uriBuilder -> webClientProperties.getPort() == 0 ?
                                uriBuilder.scheme(getProtocol())
                                        .host(webClientProperties.getHost())
                                        .path(path)
                                        .build()
                                : uriBuilder.scheme(getProtocol())
                                .host(webClientProperties.getHost())
                                .port(webClientProperties.getPort())
                                .path(path)
                                .build())
                        .header(AUTHORIZATION, token)
                        .header(X_CORRELATOR_HEADER_NAME, xCorrelator)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .body(BodyInserters.fromValue(bodyStr))
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
                        .bodyToMono(clazz)
                        .log()
        ).block();
    }

    //todo sprawdzić czemu zwykłe przekazanie body nie działa
//    Cannot construct instance of `com.camara.geofencing.model.Protocol`, problem: Unexpected value 'SubscriptionRequest'
//    at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1, column: 13] (through reference chain: com.camara.geofencing.model.SubscriptionRequest["protocol"]
    @SneakyThrows
    private <V> String convertBody(V body) {
        SimpleModule customModule = new SimpleModule();
        customModule.addSerializer(LocalDateTime.class, new LDTSerializer());

       ObjectMapper objectMapper= new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule())
               .registerModule(customModule);
        String b= objectMapper.writeValueAsString(body);
        return b;
    }

    private Mono<String> obtainAccessToken() {
        if (accessToken != null && accessToken.getAccessToken() != null
                && LocalDateTime.now().isBefore(accessToken.getExpireAt())) {
            return Mono.just(accessToken.getTokenType() + " " + accessToken.getAccessToken());
        }

        return builder.filter(logRequest()).build().post()
                .uri(webClientProperties.getTokenUrl())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + webClientProperties.getAuthHeader())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .bodyToMono(OplAccessToken.class)
                .log()
                .map(accessToken -> {
                    this.accessToken = accessToken;
                    return accessToken.getTokenType() + " " + accessToken.getAccessToken();
                });
    }

    @Override
    protected String getProtocol() {
        return webClientProperties.getProtocol();
    }
}
