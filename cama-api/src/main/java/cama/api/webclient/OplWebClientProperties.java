package cama.api.webclient;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "opl.webclient")
@ToString
public class OplWebClientProperties {

    private String host;
    private int port;
    private String authHeader;
}
