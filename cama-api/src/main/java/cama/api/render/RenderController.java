package cama.api.render;

import cama.api.generate.controller.KeepRenderRunningApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RenderController implements KeepRenderRunningApi {

    @Override
    public ResponseEntity<Void> keepRunning() {
        return ResponseEntity.noContent().build();
    }
}
