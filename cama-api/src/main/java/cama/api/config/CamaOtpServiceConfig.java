package cama.api.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "otp.service.api")
@ToString
public class CamaOtpServiceConfig {
    private String sendCodeUrl;
    private String validateCodeUrl;
    private String sendCodeMessage;
}
