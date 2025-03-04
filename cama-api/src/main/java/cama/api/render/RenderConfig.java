package cama.api.render;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "render")
@ToString
public class RenderConfig {
    private String host;
    private String protocol;
    private String url;
    private String schedule;
}
