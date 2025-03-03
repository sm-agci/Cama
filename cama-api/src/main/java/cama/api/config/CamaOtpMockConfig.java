package cama.api.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "otp.mock")
@ToString
public class CamaOtpMockConfig {
    private String codesStorageFile;
    private String validCode;
}
