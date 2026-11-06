package kh.edu.istad.infrastructure.oauth2server.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.UUID;

@Configuration
public class ClientDataInitializer {

    @Bean
    public ApplicationRunner initClients(RegisteredClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            // Internal Microservice Client (M2M / Service-to-Service)
            if (clientRepository.findByClientId("order-service-client") == null) {
                RegisteredClient orderClient = RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId("order-service-client")
                        .clientName("Order Service Internal Client")
                        .clientSecret(passwordEncoder.encode("secret123"))
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .scope("order.read")
                        .scope("order.write")
                        .scope("customer.read")   // REQUIRED for customer-service
                        .scope("customer.write")  // REQUIRED for customer-service
                        .clientSettings(ClientSettings.builder()
                                .requireAuthorizationConsent(false)
                                .build())
                        .tokenSettings(TokenSettings.builder()
                                .accessTokenTimeToLive(Duration.ofHours(2))
                                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                                .build())
                        .build();
                clientRepository.save(orderClient);
            }

            // Frontend Web Client (Next.js / SPA - Authorization Code + PKCE)
            if (clientRepository.findByClientId("nextjs-client") == null) {
                RegisteredClient webClient = RegisteredClient.withId(UUID.randomUUID().toString())
                        .clientId("nextjs-client")
                        .clientName("Next.js Frontend Client")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .redirectUri("http://localhost:3000/api/auth/callback/custom")
                        .postLogoutRedirectUri("http://localhost:3000")
                        .scope(OidcScopes.OPENID)
                        .scope(OidcScopes.PROFILE)
                        .scope("read")
                        .clientSettings(ClientSettings.builder()
                                .requireAuthorizationConsent(true)
                                .build())
                        .tokenSettings(TokenSettings.builder()
                                .accessTokenTimeToLive(Duration.ofMinutes(30))
                                .refreshTokenTimeToLive(Duration.ofDays(7))
                                .reuseRefreshTokens(false)
                                .build())
                        .build();
                clientRepository.save(webClient);
            }
        };
    }
}
