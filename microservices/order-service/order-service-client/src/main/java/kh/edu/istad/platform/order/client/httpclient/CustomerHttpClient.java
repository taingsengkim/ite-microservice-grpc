package kh.edu.istad.platform.order.client.httpclient;

import kh.edu.istad.platform.order.domain.dto.client.CustomerResponse;
import kh.edu.istad.platform.order.domain.dto.client.QueryCustomerResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@HttpExchange("/api/customers")
public interface CustomerHttpClient {

    @GetExchange
    Flux<QueryCustomerResult> queryCustomer();

    @GetExchange("/{id}")
    Flux<QueryCustomerResult> getCustomerById(String id);

    @PostExchange
    Mono<Void> createCustomer(@RequestBody QueryCustomerResult result);

}
