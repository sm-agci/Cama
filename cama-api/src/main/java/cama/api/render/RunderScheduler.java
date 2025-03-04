package cama.api.render;

import cama.api.webclient.RenderWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class RunderScheduler {

    private final RenderWebClient renderWebClient;

    @Scheduled(fixedRateString  = "${render.schedule}")
    public void keepRenderEndpointRunning(){
        renderWebClient.get();
    }
}
