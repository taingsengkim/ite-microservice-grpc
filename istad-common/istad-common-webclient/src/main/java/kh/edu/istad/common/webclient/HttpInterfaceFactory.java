package kh.edu.istad.common.webclient;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;


public class HttpInterfaceFactory {

    private final WebClient.Builder webClient;
    public HttpInterfaceFactory(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    // Generic client
    private <T> T createClient(String url, Class<T> type) {
        WebClient wc = webClient
                .baseUrl(url)
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(wc))
                .build();

        return factory.createClient(type);
    }

    private <T> T createClient(WebClient wc, Class<T> type) {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(wc))
                .build();

        return factory.createClient(type);
    }
}
