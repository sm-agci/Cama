package cama.api.webclient;

import cama.api.render.RenderConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class RenderWebClient extends WebClientBase {
    private final WebClient.Builder builder;
    private final RenderConfig renderConfig;
    public void get() {
        log.info("RENDER GET: Connecting to render app: {}", renderConfig.getUrl());
        WebClient webClient = builder.filter(logRequest()).build();
        webClient.get()
                .uri(uriBuilder -> uriBuilder.scheme(renderConfig.getProtocol())
                        .host(renderConfig.getHost())
                        .path(renderConfig.getUrl())
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
                .bodyToMono(Void.class)
                .block();

    }
}
