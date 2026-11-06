package kh.edu.istad.common.webflux.dto;
import lombok.Builder;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Map;

@Builder
public record ErrorResponse(
        int status,
        String error,
        String message,
        Instant timestamp,
        Map<String, String> errors // Useful for validation failures
) {
}
