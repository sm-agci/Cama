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

    private final WebClient webClient;
    private final OplWebClientProperties webClientProperties;
    private OplAccessToken accessToken;

    public <T, V> T post(String path, V body, String xCorrelator, Class<T> clazz) {
        log.info("OPL SANDBOX POST: Connecting to external service, with request body: {}, xCorrelator: {}", body, xCorrelator);
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
                        .body(BodyInserters.fromValue(body))
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
                        .bodyToMono(clazz)
                        .log()
        ).block();
    }

    @SneakyThrows
    private <V> String convertBody(V body) {
        SimpleModule customModule = new SimpleModule();
        customModule.addSerializer(LocalDateTime.class, new LDTSerializer());

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule())
                .registerModule(customModule);
        return objectMapper.writeValueAsString(body);
    }

    private Mono<String> obtainAccessToken() {
        if (webClientProperties.isAdditionalLogs()) {
            log.debug("Token: {}, headers: {}", accessToken, webClientProperties);
        }
        if (accessToken != null && accessToken.getAccessToken() != null
                && LocalDateTime.now().isBefore(accessToken.getExpireAt())) {
            return Mono.just(accessToken.getTokenType() + " " + accessToken.getAccessToken());
        }

        return webClient.post()
                .uri(webClientProperties.getTokenUrl())
                .header(HttpHeaders.AUTHORIZATION, "Basic " + webClientProperties.getAuthHeader())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .bodyToMono(OplAccessToken.class)
                .log()
                .map(accessToken -> {
                    this.accessToken = accessToken;
                    this.accessToken.setExprieAt();
                    return accessToken.getTokenType() + " " + accessToken.getAccessToken();
                });
    }

    @Override
    protected String getProtocol() {
        return webClientProperties.getProtocol();
    }
}
