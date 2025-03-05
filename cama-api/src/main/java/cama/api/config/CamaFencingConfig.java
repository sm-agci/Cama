package cama.api.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "fencing.service.api")
@ToString
public class CamaFencingConfig {
    private String url;
    private String simulatorUrl;
    private int radius;
    private int maxEvents;
    private int maxWindowInMin;
    private String notificationUrl;
}
