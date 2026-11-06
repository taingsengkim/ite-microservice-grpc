package kh.edu.istad.platform.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@ComponentScan(basePackages = "kh.edu.istad.platform")
public class OrderApplicationService {

    static void main(String[] args) {
        SpringApplication.run(OrderApplicationService.class, args);
    }

}
