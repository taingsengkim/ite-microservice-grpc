package kh.edu.istad.platform.order.client.adapter;

import kh.edu.istad.platform.order.client.httpclient.CustomerHttpClient;
import kh.edu.istad.platform.order.domain.dto.client.QueryCustomerResult;
import kh.edu.istad.platform.order.domain.port.out.client.CustomerServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
@AllArgsConstructor
public class CustomerServiceClientAdapter implements CustomerServiceClient {

    // private final WebClient webClient;

    private final CustomerHttpClient customerHttpClient;

    @Override
    public Flux<QueryCustomerResult> getCustomers() {
        return customerHttpClient.queryCustomer();
    }

    @Override
    public Flux<QueryCustomerResult> getCustomerById(String id) {
        return customerHttpClient.getCustomerById(id);
    }

    @Override
    public Flux<QueryCustomerResult> createCustomer(QueryCustomerResult result) {
        return null;
    }

//    @Override
//    public Flux<QueryCustomerResult> getCustomers() {
//        return webClient
//                .get()
//                .uri("/api/customers")
//                .retrieve()
//                .bodyToFlux(QueryCustomerResult.class);
//    }

//    @Override
//    public Flux<CustomerResponse> getCustomers() {
//        return webClient
//                .get()
//                .uri("/api/customers")
//                .retrieve()
//                .bodyToFlux(CustomerResponse.class);
//    }
}
