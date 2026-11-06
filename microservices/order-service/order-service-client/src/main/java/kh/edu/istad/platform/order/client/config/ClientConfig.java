package kh.edu.istad.platform.order.client.config;

import kh.edu.istad.platform.order.client.httpclient.BusinessHttpClient;
import kh.edu.istad.platform.order.client.httpclient.CustomerHttpClient;
import kh.edu.istad.platform.order.domain.port.out.client.CustomerServiceClient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.reactive.function.client.support.WebClientHttpServiceGroupConfigurer;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.service.registry.HttpServiceGroup;
import org.springframework.web.service.registry.ImportHttpServices;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;

@Configuration
@ImportHttpServices(
        group = "customer-service",
        types = {CustomerHttpClient.class},
        clientType = HttpServiceGroup.ClientType.WEB_CLIENT
)

@ImportHttpServices(
        group = "business-service",
        types = {BusinessHttpClient.class},
        clientType = HttpServiceGroup.ClientType.WEB_CLIENT
)
public class ClientConfig {

     //
    @Bean
    public WebClientHttpServiceGroupConfigurer webClientHttpServiceGroupConfigurer(
            ReactiveClientRegistrationRepository clientRegistrationRepository
    ){

        // Create Reactive Authorized Client Manager for client_credentials grant
        var clientService = new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistrationRepository);
        var authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
                clientRegistrationRepository, clientService
        );

        // Create the WebClient OAuth2 filter
        var oauthoFilter = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        // Uses the registration ID defined in application.yaml:
        oauthoFilter.setDefaultClientRegistrationId("customer-client");

        // Attach the filter to the "customer-service" group
        return groups -> {
            groups.filterByName("customer-service")
                    .forEachClient(
                            (group, clientBuilder) ->
                                    clientBuilder
                                            .defaultHeader("User-Agent", "Web Client")
                                            .defaultStatusHandler(
                                                    HttpStatusCode::isError,
                                                    response ->{
                                                        System.out.println("Error: " + response.statusCode());
                                                        return response.createException();
                                                    }
                                            )
                    );

            groups.filterByName("customer-service")
                    .forEachClient((group, clientBuilder ) -> {
                        clientBuilder.filter(oauthoFilter);
                    });
        };
    }

//    private final WebClient.Builder webClientBuilder;
//
//    @Bean
//    public CustomerServiceClient customerServiceClient() {
//        return createClient("http://customer-service", CustomerServiceClient.class);
//    }
//
//    public BusinessHttpClient businessHttpClient() {
//        return createClient("http://business-service", BusinessHttpClient.class);
//    }

//    @Bean
//    public CustomerHttpClient customerHttpClient() {
//
//        WebClient customer = webClientBuilder
//                .baseUrl("http://customer-service")
//                .build();
//
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory
//                .builderFor(WebClientAdapter.create(customer))
//                .build();
//
//        return factory.createClient(CustomerHttpClient.class);
//    }
//
//    @Bean
//    public BusinessHttpClient businessHttpClient() {
//        WebClient business = webClientBuilder
//                .baseUrl("http://business-service")
//                .build();
//
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory
//                .builderFor(WebClientAdapter.create(business))
//                .build();
//
//        return factory.createClient(BusinessHttpClient.class);
//    }

    // Generic client
//    private <T> T createClient(String url, Class<T> type) {
//        WebClient wc = webClientBuilder
//                .baseUrl(url)
//                .build();
//
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory
//                .builderFor(WebClientAdapter.create(wc))
//                .build();
//
//        return factory.createClient(type);
//    }
//
//    private <T> T createClient(WebClient wc, Class<T> type) {
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory
//                .builderFor(WebClientAdapter.create(wc))
//                .build();
//
//        return factory.createClient(type);
//    }

}

