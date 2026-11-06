package kh.edu.istad.infrastructure.oauth2server.dto;

import java.util.Set;

public record RegisterClientRequest (
        String clientId,
        String clientSecret,
        String clientName,
        Set<String> scopes,
        long accessTokenTtlMinutes
) {
}
