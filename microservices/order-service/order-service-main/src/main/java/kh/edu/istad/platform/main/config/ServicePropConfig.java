package kh.edu.istad.platform.main.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "service")
public class ServicePropConfig {
    private String message;
    private String version;
    private String env;
    private String baseUrl = "Hello"; // Auto => base-url
}
