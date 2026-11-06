package kh.edu.istad.common.webclient.config;

import kh.edu.istad.common.webclient.HttpInterfaceFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@AutoConfiguration

// Class-level:
// Only activate if WebClient and HttpServiceProxyFactory exist on classpath
@ConditionalOnClass({WebClient.class, HttpServiceProxyFactory.class})

// Activate in either reactive environments or standard web services using WebClient
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.ANY)
public class CommonWebClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(WebClient.Builder.class)
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    @ConditionalOnMissingBean(HttpInterfaceFactory.class)
    public HttpInterfaceFactory httpServiceProxyFactory(WebClient.Builder webClientBuilder) {
        return new HttpInterfaceFactory(webClientBuilder);
    }

}
