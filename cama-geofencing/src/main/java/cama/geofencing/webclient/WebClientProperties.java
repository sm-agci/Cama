package cama.geofencing.webclient;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "webclient")
@ToString
public class WebClientProperties {

    private String host;
    private int port;
}
