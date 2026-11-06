package kh.edu.istad.platform.main;

import kh.edu.istad.platform.main.config.ServicePropConfig;
import kh.edu.istad.platform.order.domain.dto.client.CustomerResponse;
import kh.edu.istad.platform.order.domain.dto.client.QueryCustomerResult;
import kh.edu.istad.platform.order.domain.dto.client.QueryProductResult;
import kh.edu.istad.platform.order.domain.port.out.client.CustomerServiceClient;
import kh.edu.istad.platform.order.domain.port.out.client.ProductServiceClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping
//@RefreshScope
public class WebFluxController {

    private final CustomerServiceClient customerServiceClient;
    private final ProductServiceClient productServiceClient;
    private final ServicePropConfig servicePropConfig;

    public WebFluxController(CustomerServiceClient customerServiceClient, ProductServiceClient productServiceClient, ServicePropConfig servicePropConfig) {
        this.customerServiceClient = customerServiceClient;
        this.productServiceClient = productServiceClient;
        this.servicePropConfig = servicePropConfig;
    }


//    @Value("${service.message:Default Message}")
//    private String serviceMessage;
//
//    @Value("${service.version:1.0.0}")
//    private String version;
//
//    @Value("${service.env:local}")
//    private String env;

//    @GetMapping("/config-check")
//    public Mono< Map<String, String> > getConfig() {
//        return Mono.just(
//                Map.of(
//                        "serviceMessage", servicePropConfig.getMessage(),
//                        "version", servicePropConfig.getVersion(),
//                        "env", servicePropConfig.getEnv(),
//                        "baseUrl", servicePropConfig.getBaseUrl()
//                )
//        );
//    }

    static class WebFluxControllerService {

    }

    @GetMapping("/api/customers")
    public Flux<QueryCustomerResult> getCustomers() {
        return customerServiceClient.getCustomers();
    }


//    @GetMapping("/api/customers")
//    public Flux<CustomerResponse> getCustomers() {
//        return customerServiceClient.getCustomers();
//    }
//
//    @GetMapping("/customers")
//    public Flux<CustomerResponse> getCustomersLegacy() {
//        return customerServiceClient.getCustomers();
//    }

    @GetMapping("/api/customers/{id}")
    public Flux<QueryCustomerResult> getCustomerById(@PathVariable String id) {
        return customerServiceClient.getCustomerById(id);
    }

    @GetMapping("/flux")
    public Flux<String> getFluxData() {
        return Flux.just("A", "B", "C", "D", "E", "F")
                .log()
                .delayElements(Duration.ofSeconds(1));
    }

    // Endpoint calling the gRPC
    @GetMapping("/api/products/{id}")
    public Mono<QueryProductResult> getProductById(@PathVariable String id) {
        return productServiceClient.getProductById(id);
    }

}
