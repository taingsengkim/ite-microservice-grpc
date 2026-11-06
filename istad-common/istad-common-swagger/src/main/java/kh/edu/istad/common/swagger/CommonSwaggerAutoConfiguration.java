package kh.edu.istad.common.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
//@OpenAPIDefinition(
//        info = @Info(
//                title = "ISTAD Microservices API",
//                version = "1.0.0",
//                description = "Documentation Microservices endpoints",
//                contact = @Contact(name = "ISTAD Team", email = "dev@istad.edu.kh"),
//                license = @License(name = "MIT License")
//        )
//)
public class CommonSwaggerAutoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .info(new Info()
                                .title("ISTAD Microservices APIs")
                                .version("1.0.0")
                                .description("Documentation Microservices endpoints")
                                .contact(new Contact()
                                        .name("ISTAD Team")
                                        .email("dev@istad.edu.kh"))
                                .license(new License()
                                        .name("MIT License")));
        }


}
