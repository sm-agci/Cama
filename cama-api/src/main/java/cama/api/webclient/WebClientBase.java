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

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
abstract class WebClientBase {
    private static final String DEFAULT_PROTOCOL = "http";
    protected static final String X_CORRELATOR_HEADER_NAME = "x-correlator";

//    private final WebClientRetryFactory retryFactory;

    public <T, V> T post(WebClient webClient, String host, int port, String path, Map<String, String> headers, V body, Class<T> clazz) {
        log.info("POST: Connecting to external service, with request body: {}", body);
        WebClient.RequestBodySpec spec = webClient.post()
                .uri(uriBuilder -> port == 0 ?
                        uriBuilder.scheme(getProtocol())
                                .host(host)
                                .path(path)
                                .build()
                        : uriBuilder.scheme(getProtocol())
                        .host(host)
                        .port(port)
                        .path(path)
                        .build());
        headers.entrySet().stream().forEach((entry) -> spec.header(entry.getKey(), entry.getValue()));
        T response = spec.body(BodyInserters.fromValue(body))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
                .bodyToMono(clazz)
//                .retryWhen(retryFactory.retrySpec())
               .block();
        log.info("External service response: {}", response);
        return response;
    }

    protected String getProtocol() {
        return DEFAULT_PROTOCOL;
    }

    protected Mono<? extends Throwable> handleClientError(ClientResponse clientResponse) {
        HttpStatusCode statusCode = clientResponse.statusCode();
        log.warn("Detected client error response with status: {}", statusCode);
        return clientResponse.createException();
    }
}
