package cama.api.controller;

import cama.api.config.CamaOtpConfig;
import cama.api.config.CamaOtpMockConfig;
import cama.api.config.CamaOtpServiceConfig;
import cama.api.generate.dto.SendCodeBody;
import cama.api.generate.dto.SendCodeResponse;
import cama.api.generate.dto.ValidateCodeBody;
import cama.api.local.otp.CamaOtpLocalMockService;
import cama.api.webclient.CamaOtpWebClient;
import ch.qos.logback.core.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
class CamaApiOtpService {

    private final CamaOtpServiceConfig camaOtpServiceConfig;
    private final CamaOtpWebClient webClient;
    private final CamaOtpLocalMockService localMockService;
    private final CamaOtpConfig otpConfig;

    SendCodeResponse sendCode(SendCodeBody otpMessage, String xCorrelator) {
        if (StringUtil.isNullOrEmpty(otpMessage.getMessage())) {
            otpMessage.setMessage(camaOtpServiceConfig.getSendCodeMessage());
        }
        if (otpConfig.isUseExternalService()) {
            return webClient.post(camaOtpServiceConfig.getSendCodeUrl(),
                    otpMessage, xCorrelator, SendCodeResponse.class);
        } else {
            String code = localMockService.sendCode(otpMessage, xCorrelator);
            return new SendCodeResponse(String.format("%s|%s",code, xCorrelator));
        }
    }

    void validateCode(ValidateCodeBody otpValidateCode, String xCorrelator) {
        if (otpConfig.isUseExternalService()) {
            webClient.post(camaOtpServiceConfig.getValidateCodeUrl(),
                    otpValidateCode, xCorrelator, Void.class);
        } else {
            localMockService.validateCode(otpValidateCode, xCorrelator);
        }
    }
}
