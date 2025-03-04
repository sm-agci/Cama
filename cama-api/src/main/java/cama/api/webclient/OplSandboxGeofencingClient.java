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
public class OplSandboxGeofencingClient extends WebClientBase {
    private static final String AUTHORIZATION = "Authorization";
    private static final String PROTOCOL = "https";

    private final WebClient.Builder webClientBuilder;
    private final OplWebClientProperties webClientProperties;

    public <T, V> T post(String path, V body, String xCorrelator, Class<T> clazz) {
        log.info("OTP MOCK POST: Connecting to external service, with request body: {}, xCorrelator: {}", body, xCorrelator);
        Map<String, String> headers = new HashMap<>();
        headers.put(AUTHORIZATION, "Basic " + webClientProperties.getAuthHeader());
        headers.put(X_CORRELATOR_HEADER_NAME, xCorrelator);
        return post(webClientBuilder, webClientProperties.getHost(), webClientProperties.getPort(),
                path, headers, body, clazz);
    }

    @Override
    protected String getProtocol() {
        return PROTOCOL;
    }
}
