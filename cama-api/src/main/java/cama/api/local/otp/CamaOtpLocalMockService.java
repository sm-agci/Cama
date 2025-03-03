package cama.api.local.otp;

import cama.api.config.CamaOtpMockConfig;
import cama.api.generate.dto.SendCodeBody;
import cama.api.generate.dto.ValidateCodeBody;
import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Slf4j
@Component
@RequiredArgsConstructor
public class CamaOtpLocalMockService {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private final CamaOtpMockConfig mockConfig;
    private final SecureRandom rnd = new SecureRandom();
    private final CodesStorage storage;

    public String sendCode(SendCodeBody otpMessage, String xCorrelator) {
        String code = generateOtpCode();
        storage.sendCode(otpMessage.getPhoneNumber(), code, xCorrelator);
        log.debug("[sendCode] generatedCode = {}, message= {}, x-correlator: {}", code, otpMessage, xCorrelator);
        return code;
    }

    public void validateCode(ValidateCodeBody otpValidateCode, String xCorrelator) {
        log.debug("[validateCode] generatedCode = {}, x-correlator: {}", otpValidateCode, xCorrelator);
        boolean isValid =!StringUtil.isNullOrEmpty(mockConfig.getValidCode()) && mockConfig.getValidCode().equals(otpValidateCode.getCode()) ?
                true:
                storage.validateCode(otpValidateCode.getCode(), otpValidateCode.getAuthenticationId());
        if (!isValid) {
            throw new InvalidOtpCodeException();
        }
    }

    private String generateOtpCode() {
        StringBuilder code = new StringBuilder();
        while (code.length() < 4) {
            int index = rnd.nextInt(1, CHARS.length());
            code.append(CHARS.charAt(index));
        }
        return code.toString();
    }
}
