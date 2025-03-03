package cama.otp.service.controller;

import cama.otp.service.generate.dto.SendCodeBody;
import cama.otp.service.generate.dto.ValidateCodeBody;
import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import cama.otp.service.codes.CodesStorage;
import cama.otp.service.exceptions.InvalidOtpCodeException;

import java.security.SecureRandom;

@Slf4j
@Component
@RequiredArgsConstructor
public class CamaOtpService {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    @Value("${api.valid.code}")
    private String validCode;
    private final SecureRandom rnd = new SecureRandom();
    private final CodesStorage storage;

    String sendCode(SendCodeBody otpMessage, String xCorrelator) {
        String code = generateOtpCode();
        storage.sendCode(otpMessage.getPhoneNumber(), code, xCorrelator);
        log.debug("[sendCode] generatedCode = {}, message= {}, x-correlator: {}", code, otpMessage, xCorrelator);
        return code;
    }

    void validateCode(ValidateCodeBody otpValidateCode, String xCorrelator) {
        log.debug("[validateCode] generatedCode = {}, x-correlator: {}", otpValidateCode, xCorrelator);
        boolean isValid =!StringUtil.isNullOrEmpty(validCode) && validCode.equals(otpValidateCode.getCode()) ?
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
//todo send to external url
//todo add local storage?