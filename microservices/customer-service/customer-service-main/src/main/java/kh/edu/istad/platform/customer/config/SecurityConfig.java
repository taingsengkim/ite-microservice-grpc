package kh.edu.istad.platform.customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // Public endpoints
                        .pathMatchers("/actuator/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // Protect Customer APIs with OAuth2 Scopes
                        .pathMatchers(HttpMethod.GET, "/api/customers/**").hasAuthority("SCOPE_customer.read")
                        .pathMatchers(HttpMethod.POST, "/api/customers/**").hasAuthority("SCOPE_customer.write")
                        .pathMatchers(HttpMethod.PUT, "/api/customers/**").hasAuthority("SCOPE_customer.write")
                        .pathMatchers(HttpMethod.DELETE, "/api/customers/**").hasAuthority("SCOPE_customer.write")
                        .anyExchange().authenticated()
                )
                // Enables JWT validation via issuer-uri
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }

}
