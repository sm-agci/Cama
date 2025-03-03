package cama.api.controller;

import cama.api.config.CamaOtpMockConfig;
import cama.api.generate.dto.SendCodeBody;
import cama.api.generate.dto.SendCodeResponse;
import cama.api.generate.dto.ValidateCodeBody;
import cama.api.local.otp.CamaOtpLocalMockService;
import cama.api.webclient.CamaOtpWebClient;
import ch.qos.logback.core.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
class CamaApiOtpService {

    private final CamaOtpMockConfig camaOtpMockConfig;
    private final CamaOtpWebClient webClient;
    private final CamaOtpLocalMockService localMockService;

    SendCodeResponse sendCode(SendCodeBody otpMessage, String xCorrelator) {
        if (StringUtil.isNullOrEmpty(otpMessage.getMessage())) {
            otpMessage.setMessage(camaOtpMockConfig.getSendCodeMessage());
        }
        if (camaOtpMockConfig.isUseExternalService()) {
            return webClient.post(camaOtpMockConfig.getSendCodeUrl(),
                    otpMessage, xCorrelator, SendCodeResponse.class);
        } else {
            String authId = localMockService.sendCode(otpMessage, xCorrelator);
            return new SendCodeResponse(authId);
        }
    }

    void validateCode(ValidateCodeBody otpValidateCode, String xCorrelator) {
        if (camaOtpMockConfig.isUseExternalService()) {
            webClient.post(camaOtpMockConfig.getValidateCodeUrl(),
                    otpValidateCode, xCorrelator, Void.class);
        } else {
            localMockService.validateCode(otpValidateCode, xCorrelator);
        }
    }
}
