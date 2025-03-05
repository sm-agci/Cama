package cama.api.controller;

import cama.api.generate.controller.CamaApiApi;
import cama.api.generate.controller.DefaultApi;
import cama.api.generate.dto.*;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CamaApiController implements CamaApiApi {

    private final CamaApiOtpService camaApiOtpService;
    private final CamaApiFencingService camaApiFencingService;

    @Override
    public ResponseEntity<SendCodeResponse> sendCode(SendCodeBody otpMessage) {
        String xCorrelator = UUID.randomUUID().toString();
        MDC.put("uniqueId", xCorrelator);
        log.debug("[sendCode] otpMessage = {}", otpMessage);
        SendCodeResponse response = camaApiOtpService.sendCode(otpMessage, xCorrelator);
        MDC.clear();
        return ResponseEntity.ok()
                .body(response);
    }

    @Override
    public ResponseEntity<Object> validateCode(ValidateCodeBody otpValidateCode) {
        String xCorrelator = UUID.randomUUID().toString();
        MDC.put("uniqueId", xCorrelator);
        log.debug("[validateCode] otpValidateCode = {}", otpValidateCode);
        camaApiOtpService.validateCode(otpValidateCode, xCorrelator);
        MDC.clear();
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TaskResponse> createTask(Task task) {
        String xCorrelator = UUID.randomUUID().toString();
        MDC.put("uniqueId", xCorrelator);
        log.debug("[createTask] task = {}", task);
        TaskResponse taskResponse = camaApiFencingService.createTask(task, xCorrelator);
        MDC.clear();
        return ResponseEntity.ok()
                .body(taskResponse);
    }

    @Override
    public ResponseEntity<Void> deleteTask(String id) {
        String xCorrelator = UUID.randomUUID().toString();
        MDC.put("uniqueId", xCorrelator);
        log.debug("[deleteTask] taskId = {}", id);
        camaApiFencingService.deleteTask(id, xCorrelator);
        MDC.clear();
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TaskResponse> getTask(String id) {
        String xCorrelator = UUID.randomUUID().toString();
        MDC.put("uniqueId", xCorrelator);
        log.debug("[getTask] taskId = {}", id);
        TaskResponse taskResponse = camaApiFencingService.getTask(id, xCorrelator);
        MDC.clear();
        return ResponseEntity.ok()
                .body(taskResponse);
    }

    @Override
    public ResponseEntity<List<TaskResponse>> getTasks(String phoneNumber) {
        String xCorrelator = UUID.randomUUID().toString();
        MDC.put("uniqueId", xCorrelator);
        log.debug("[getTask] x-correlator: {}", xCorrelator);
        List<TaskResponse> tasks = camaApiFencingService.getTasks(phoneNumber);
        MDC.clear();
        return ResponseEntity.ok()
                .body(tasks);
    }
}
