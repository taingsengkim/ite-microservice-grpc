package kh.edu.istad.platform.order.domain.port.out.client;

import kh.edu.istad.platform.order.domain.dto.client.QueryCustomerResult;
import reactor.core.publisher.Flux;

public interface CustomerServiceClient {

    Flux<QueryCustomerResult> getCustomers();

    Flux<QueryCustomerResult> getCustomerById(String id);

    Flux<QueryCustomerResult> createCustomer(QueryCustomerResult result);

}
