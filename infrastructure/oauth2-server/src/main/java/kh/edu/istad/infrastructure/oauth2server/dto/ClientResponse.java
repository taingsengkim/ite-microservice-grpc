package kh.edu.istad.infrastructure.oauth2server.dto;

import java.time.Instant;
import java.util.Set;

public record ClientResponse (
        String id,
        String clientId,
        String clientName,
        Set<String> clientAuthenticationMethods,
        Set<String> authorizationGrantTypes,
        Set<String> scopes,
        Set<String> redirectUris,
        Instant clientIdIssuedAt
) {
}
