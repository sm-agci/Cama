package cama.api.webclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class CamaOtpMockWebClient {
    private static final String DEFAULT_PROTOCOL = "http";
    private static final String API_TOKEN_HEADER_NAME = "X-API-KEY";
    private static final String X_CORRELATOR_HEADER_NAME = "x-correlator";

    private final WebClient.Builder webClientBuilder;
    private final WebClientProperties webClientProperties;
    public <T, V> T post(String path, String token, V body, String xCorrelator, Class<T> clazz) {
        log.info("POST: Connecting to external service, with request body: {}, xCorrelator: {}", body, xCorrelator);
        WebClient webClient = webClientBuilder.filter(logRequest()).build();
        T response = webClient.post()
                .uri(uriBuilder -> uriBuilder.scheme(DEFAULT_PROTOCOL)
                        .host(webClientProperties.getHost())
                        .port(webClientProperties.getPort())
                        .path(path)
                        .build())
                .header(X_CORRELATOR_HEADER_NAME, xCorrelator)
                .header(API_TOKEN_HEADER_NAME, token)
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
                .bodyToMono(clazz)
                .block();
        log.info("External service response: {}", response);
        return response;
    }
//    public <T, V> T get(String path, String token, V body, Class<T> clazz) {
//        log.info("GET: Connecting to external service, with request body: {}", body);
//        WebClient webClient = webClientBuilder.build();
//        T response = webClient.post()
//                .uri(uriBuilder -> uriBuilder.scheme(DEFAULT_PROTOCOL)
//                        .host(webClientProperties.getHost())
//                        .port(webClientProperties.getPort())
//                        .path(path)
//                        .build())
//                .header(X_CORRELATOR_HEADER_NAME, "")
//                .header(API_TOKEN_HEADER_NAME, token)
//                .body(BodyInserters.fromValue(body))
//                .retrieve()
//                .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
//                .bodyToMono(clazz)
//                .block();
//        log.info("External service response: {}", response);
//        return response;
//    }


    private Mono<? extends Throwable> handleClientError(ClientResponse clientResponse) {
        HttpStatusCode statusCode = clientResponse.statusCode();
        log.warn("Detected client error response with status: {}", statusCode);
        return clientResponse.createException();
    }

    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return Mono.just(clientRequest);
        });
    }
}
