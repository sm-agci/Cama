package tapis.cama.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import cama.otp.mock.generate.dto.SendCodeResponse;
import cama.otp.mock.generate.dto.SendCodeBody;
import cama.otp.mock.generate.dto.ValidateCodeBody;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CamaOtpMockController implements cama.otp.mock.generate.controller.OtpManagementApi {

    private final CamaOtpMockService service;
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
