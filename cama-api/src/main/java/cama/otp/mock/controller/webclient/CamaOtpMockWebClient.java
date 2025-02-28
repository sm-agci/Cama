package tapis.geo.gmlc.facade.webclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tapis.geo.gmlc.facade.api.GmlcLocation;
import tapis.geo.gmlc.facade.webclient.config.GmlcEndpointConfig;
import tapis.geo.gmlc.facade.webclient.config.WebClientProperties;
import tapis.geo.gmlc.facade.webclient.loadbalancer.WebClientRetryFactory;

@Slf4j
@RequiredArgsConstructor
@Component
public class GmlcWebClient {

    private static final String SECURED_PROTOCOL = "https";
    private static final String DEFAULT_PROTOCOL = "http";
    private static final String LOADBALANCED_CLIENT_NAME = "gmlc";

    private final WebClientRetryFactory retryFactory;
    private final WebClient.Builder webClientBuilder;
    private final WebClientProperties webClientProperties;
    private final GmlcEndpointConfig gmlcEndpointConfig;

    public GmlcLocation get(String msisdn) {
        log.info("Connecting to external service with msisdn: {}", msisdn);
        String schema = resolveSchema();
        GmlcLocation response = webClientBuilder.build().get()
                .uri(uriBuilder -> uriBuilder.scheme(schema)
                        .host(LOADBALANCED_CLIENT_NAME)
                        .path(gmlcEndpointConfig.getEndpointUrl())
                        .queryParam("msisdn", "{msisdn}")
                        .queryParam("apikey", "{apikey}")
                        .build(msisdn, gmlcEndpointConfig.getApiKey()))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
                .bodyToMono(GmlcLocation.class)
                .retryWhen(retryFactory.retrySpec())
                .block();

        log.info("External service response: {}", response);
        return response;
    }

    private String resolveSchema() {
        return webClientProperties.isTlsEnabled() ? SECURED_PROTOCOL : DEFAULT_PROTOCOL;
    }

    private Mono<? extends Throwable> handleClientError(ClientResponse clientResponse) {
        HttpStatusCode statusCode = clientResponse.statusCode();
        log.warn("Detected client error response with status: {}", statusCode);
        return clientResponse.createException();
    }
}

//todo lb check
//todo docker
//todo utils
//todo helm

