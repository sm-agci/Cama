package cama.api.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "cama.otp.mock.api")
@ToString
public class CamaOtpMockConfig {
    private String sendCodeUrl;
    private String validateCodeUrl;
    private String token;
    private String sendCodeMessage;
}
