package kh.edu.istad.infrastructure.oauth2server.controller;

import kh.edu.istad.infrastructure.oauth2server.dto.ClientResponse;
import kh.edu.istad.infrastructure.oauth2server.dto.RegisterClientRequest;
import kh.edu.istad.infrastructure.oauth2server.entity.Client;
import kh.edu.istad.infrastructure.oauth2server.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import kh.edu.istad.infrastructure.oauth2server.repository.ClientRepository;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/clients")
@RequiredArgsConstructor
public class ClientRegistrationController {

    private final RegisteredClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientJpaRepository;

    // Get All Clients
    @GetMapping
    public List<ClientResponse> getClients() {
        return clientJpaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    // GET CLIENT BY CLIENT_ID
    @GetMapping("/{clientId}")
    public ClientResponse getClientByClientId(@PathVariable String clientId) {
        return clientJpaRepository.findByClientId(clientId)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found with clientId: " + clientId));
    }

    // Delete Client By ID
    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClientByClientId(@PathVariable String clientId) {
        Client client = clientJpaRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Client not found with clientId: " + clientId
                ));
        clientJpaRepository.delete(client);
    }

    // Register M2M Client
    @PostMapping("/m2m")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerM2MClient(@RequestBody RegisterClientRequest request) {
        if (clientRepository.findByClientId(request.clientId()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Client ID already exists: " + request.clientId());
        }

        RegisteredClient.Builder builder = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(request.clientId())
                .clientName(request.clientName())
                .clientSecret(passwordEncoder.encode(request.clientSecret()))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(false)
                        .build())
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(
                                request.accessTokenTtlMinutes() > 0 ? request.accessTokenTtlMinutes() : 60))
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                        .build());

        // Assign domain scopes (e.g., payment.read, product.write)
        if (request.scopes() != null) {
            request.scopes().forEach(builder::scope);
        }

        clientRepository.save(builder.build());

        return ResponseEntity.ok("Successfully registered client: " + request.clientId());
    }

    // Helper mapper from JPA entity to safe DTO
    private ClientResponse toResponse(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getClientId(),
                client.getClientName(),
                StringUtils.commaDelimitedListToSet(client.getClientAuthenticationMethods()),
                StringUtils.commaDelimitedListToSet(client.getAuthorizationGrantTypes()),
                StringUtils.commaDelimitedListToSet(client.getScopes()),
                StringUtils.commaDelimitedListToSet(client.getRedirectUris()),
                client.getClientIdIssuedAt()
        );
    }

}
