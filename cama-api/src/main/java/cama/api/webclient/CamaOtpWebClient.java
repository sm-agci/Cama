package cama.api.webclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class CamaOtpWebClient extends WebClientBase {
    private static final String API_TOKEN_HEADER_NAME = "X-API-KEY";

    private final WebClient webClient;
    private final OtpWebClientProperties webClientProperties;

    public <T, V> T post(String path, V body, String xCorrelator, Class<T> clazz) {
        log.info("OTP MOCK POST: Connecting to external service, with request body: {}, xCorrelator: {}", body, xCorrelator);
        Map<String, String> headers = new HashMap<>();
        headers.put(API_TOKEN_HEADER_NAME, webClientProperties.getApiKey());
        headers.put(X_CORRELATOR_HEADER_NAME, xCorrelator);
        return post(webClient, webClientProperties.getHost(), webClientProperties.getPort(),
                path, headers, body, clazz);
    }
}
