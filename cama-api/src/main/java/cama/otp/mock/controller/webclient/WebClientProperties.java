package tapis.geo.gmlc.facade.webclient.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "webclient")
@ToString
public class WebClientProperties {

    private Duration retryDelayMs;
    private Integer retryAttempts;
    private boolean tlsEnabled;
    private String tlsBundleName;
    private List<ServiceInstanceProperties> servers;
}
