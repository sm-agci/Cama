package cama.otp.service.codes;

import cama.otp.service.exceptions.StorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
@Slf4j
public class CodesStorage {
    @Value("${otp.codes.storage.file}")
    private String storageFile;

    private static final String COMMA_DELIMITER = ",";

    public void sendCode(String phoneNumber, String code, String xCorrelator) {
        try (FileOutputStream fos = new FileOutputStream(storageFile, true)) {
            String line = formatLine(phoneNumber, code, xCorrelator);
            fos.write(line.getBytes());
        } catch (IOException e) {
            log.error("Unable to add to storage: {}", e);
            throw new StorageException();
        }
    }

    public boolean validateCode(String code, String authenticationId) {
        List<OtpCode> codes = getCodes();
        return codes.stream()
                .anyMatch(x -> x.getCode().equals(code) && x.getXCorrelator().equals(authenticationId));
    }

    private List<OtpCode> getCodes() {
        try {
            List<OtpCode> records = Files.readAllLines(Paths.get(storageFile))
                    .stream()
                    .map(line -> new OtpCode(line.split(COMMA_DELIMITER)))
                    .toList();
            log.debug("Read codes: {}", records);
            return records;
        } catch (IOException e) {
            log.error("Unable to access storage: {}", e);
            throw new StorageException();
        }
    }

    private static String formatLine(String phoneNumber, String code, String xCorrelator) {
        return String.format("%s,%s,%s\n", phoneNumber, code, xCorrelator);
    }
}
