package cama.otp.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import cama.otp.service.generate.dto.SendCodeResponse;
import cama.otp.service.generate.dto.SendCodeBody;
import cama.otp.service.generate.dto.ValidateCodeBody;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CamaOtpController implements cama.otp.service.generate.controller.OtpManagementApi {

    private final CamaOtpService service;
    @Override
    public ResponseEntity<SendCodeResponse> sendCode(SendCodeBody otpMessage, String xCorrelator) {
        log.debug("[sendCode] otpMessage = {}, x-correlator: {}", otpMessage, xCorrelator);
        service.sendCode(otpMessage, xCorrelator);
        return ResponseEntity.ok()
                .body(new SendCodeResponse(xCorrelator));
    }

    @Override
    public ResponseEntity<Void> validateCode(ValidateCodeBody otpValidateCode, String xCorrelator) {
        log.debug("[validateCode] otpValidateCode = {}, x-correlator: {}", otpValidateCode, xCorrelator);
        service.validateCode(otpValidateCode, xCorrelator);
        return ResponseEntity.noContent().build();
    }
}
