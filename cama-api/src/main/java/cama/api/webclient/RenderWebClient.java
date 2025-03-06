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
    private final WebClient webClient;
    private final RenderConfig renderConfig;

    public void get() {
        log.info("RENDER GET: Connecting to render app: {}", renderConfig.getUrl());
        webClient.get()
                .uri(uriBuilder -> renderConfig.getPort() != 0 ?
                        uriBuilder.scheme(renderConfig.getProtocol())
                                .host(renderConfig.getHost())
                                .port(renderConfig.getPort())
                                .path(renderConfig.getUrl())
                                .build()
                        : uriBuilder.scheme(renderConfig.getProtocol())
                        .host(renderConfig.getHost())
                        .path(renderConfig.getUrl())
                        .build())
                .retrieve()
                .toBodilessEntity()
                .doOnSuccess(e -> log.debug("RENDER status: {}", e.getStatusCode()))
                .doOnError(e -> log.error("RENDER :{}", e.getMessage()))
                .subscribe();

    }
}
