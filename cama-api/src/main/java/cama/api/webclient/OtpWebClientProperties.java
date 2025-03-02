package cama.api.webclient;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "otp.webclient")
@ToString
public class OtpWebClientProperties {

    private String host;
    private int port;
    private String apiKey;
}
