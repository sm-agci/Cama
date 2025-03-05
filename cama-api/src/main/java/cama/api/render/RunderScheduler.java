package cama.api.render;

import cama.api.webclient.RenderWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@Slf4j
@Component
@RequiredArgsConstructor
class RunderScheduler {

    private final RenderWebClient renderWebClient;
    private final RenderConfig config;

    @Scheduled(fixedRateString = "${render.schedule}", initialDelay = 5000)
    public void keepRenderEndpointRunning() {
        log.debug("Scheduled call to render, config: {}", config.getSchedule());
        try {
            renderWebClient.get();
        } catch (WebClientRequestException e) {
            log.error("Unable to process: {}", e);
        }
    }
}
