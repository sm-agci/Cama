package cama.api.controller;

import cama.api.config.CamaOtpMockConfig;
import cama.api.generate.dto.SendCodeBody;
import cama.api.generate.dto.SendCodeResponse;
import cama.api.generate.dto.ValidateCodeBody;
import cama.api.webclient.CamaOtpWebClient;
import ch.qos.logback.core.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
class CamaApiFencingService {

    private final CamaOtpMockConfig camaOtpMockConfig;
    private final CamaOtpWebClient webClient;

    SendCodeResponse sendCode(SendCodeBody otpMessage, String xCorrelator) {
        if (StringUtil.isNullOrEmpty(otpMessage.getMessage())) {
            otpMessage.setMessage(camaOtpMockConfig.getSendCodeMessage());
        }
        return webClient.post(camaOtpMockConfig.getSendCodeUrl(),
                otpMessage, xCorrelator, SendCodeResponse.class);
    }

    void validateCode(ValidateCodeBody otpValidateCode, String xCorrelator) {
        webClient.post(camaOtpMockConfig.getValidateCodeUrl(),
                otpValidateCode, xCorrelator, Void.class);
    }
}
