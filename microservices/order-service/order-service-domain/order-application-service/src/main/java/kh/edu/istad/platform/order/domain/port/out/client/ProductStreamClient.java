package kh.edu.istad.platform.order.domain.port.out.client;

import kh.edu.istad.platform.order.domain.dto.client.QueryProductResult;
import reactor.core.publisher.Flux;

public interface ProductStreamClient {
    Flux<QueryProductResult> listProducts();
}
