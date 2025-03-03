package cama.api.local.otp;

import cama.api.config.CamaOtpMockConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CodesStorage {

    private final CamaOtpMockConfig camaOtpMockConfig;
    private final FileCodesStorage fileCodesStorage;
    private final MemoryCodesStorage memoryCodesStorage;

    public void sendCode(String phoneNumber, String code, String xCorrelator) {
        if (camaOtpMockConfig.getSource().equalsIgnoreCase("FILE")) {
            fileCodesStorage.sendCode(phoneNumber, code, xCorrelator);
        } else {
            memoryCodesStorage.sendCode(phoneNumber, code, xCorrelator);
        }
    }

    public boolean validateCode(String code, String authenticationId) {
        if (camaOtpMockConfig.getSource().equalsIgnoreCase("FILE")) {
           return fileCodesStorage.validateCode(code, authenticationId);
        } else {
           return memoryCodesStorage.validateCode(code, authenticationId);
        }
    }

}
