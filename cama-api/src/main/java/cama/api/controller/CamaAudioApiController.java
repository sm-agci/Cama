package cama.api.controller;

import cama.api.generate.controller.CamaApiApi;
import cama.api.generate.controller.DefaultApi;
import cama.api.generate.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CamaAudioApiController implements  DefaultApi {

    @Override
    public ResponseEntity<TaskResponse> createTaskFromFile(MultipartFile file) {
        String xCorrelator = UUID.randomUUID().toString();
        MDC.put("uniqueId", xCorrelator);
        log.debug("[createTask-Audio] task = {}, x-correlator: {}", file.getName(), xCorrelator);
        TaskResponse taskResponse = null; //todo add impl
        MDC.clear();
        return ResponseEntity.ok()
                .body(taskResponse);
    }
}
