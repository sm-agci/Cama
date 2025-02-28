package tapis.geo.gmlc.facade.webclient.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "gmlc")
@ToString
public class GmlcEndpointConfig {
    private String endpointUrl;
    private String apiKey;
}
