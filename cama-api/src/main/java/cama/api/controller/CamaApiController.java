package cama.api.controller;

import cama.api.generate.controller.CamaApiApi;
import cama.api.generate.dto.SendCodeBody;
import cama.api.generate.dto.SendCodeResponse;
import cama.api.generate.dto.ValidateCodeBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CamaApiController implements CamaApiApi {

    private final CamaApiService camaApiService;

    @Override
    public ResponseEntity<SendCodeResponse> sendCode(SendCodeBody otpMessage) {
        String xCorrelator = UUID.randomUUID().toString();
        MDC.put("uniqueId", xCorrelator);
        log.debug("[sendCode] otpMessage = {}, x-correlator: {}", otpMessage, xCorrelator);
        SendCodeResponse response = camaApiService.sendCode(otpMessage, xCorrelator);
        MDC.clear();
        return ResponseEntity.ok()
                .body(response);
    }

    @Override
    public ResponseEntity<Object> validateCode(ValidateCodeBody otpValidateCode) {
        String xCorrelator = UUID.randomUUID().toString();
        MDC.put("uniqueId", xCorrelator);
        log.debug("[validateCode] otpValidateCode = {}, x-correlator: {}", otpValidateCode, xCorrelator);
        camaApiService.validateCode(otpValidateCode, xCorrelator);
        MDC.clear();
        return ResponseEntity.noContent().build();
    }
}
