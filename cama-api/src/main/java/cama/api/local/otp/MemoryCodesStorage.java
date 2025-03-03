package cama.api.local.otp;

import cama.api.config.CamaOtpMockConfig;
import ch.qos.logback.core.util.StringUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemoryCodesStorage {

    private final CamaOtpMockConfig camaOtpMockConfig;
    private Map<String, List<OtpCode>> codes = new HashMap<>();

    public void sendCode(String phoneNumber, String code, String xCorrelator) {
        if (codes.containsKey(phoneNumber)) {
            boolean exists = codes.values()
                    .stream().flatMap(Collection::stream)
                    .anyMatch(x -> x.getCode().equals(code) && x.getXCorrelator().equals(xCorrelator));
            if (!exists) {
                OtpCode otp = new OtpCode(phoneNumber, code, xCorrelator);
                codes.get(phoneNumber).add(otp);
            }
        } else {
            OtpCode otp = new OtpCode(phoneNumber, code, xCorrelator);
            ArrayList list = new ArrayList();
            list.add(otp);
            codes.put(phoneNumber, list);
        }
    }

    public boolean validateCode(String code, String authenticationId) {
        return codes.values()
                .stream().flatMap(Collection::stream)
                .anyMatch(x -> x.getCode().equals(code) && x.getXCorrelator().equals(authenticationId));
    }
}
