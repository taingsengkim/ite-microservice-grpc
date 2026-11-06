package kh.edu.istad.common.webflux.exception;

import kh.edu.istad.common.domain.exception.DomainException;
import kh.edu.istad.common.webflux.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class DomainExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleDomainException(DomainException exception) {

        log.warn("Domain exception caught: {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .timestamp(Instant.now())
                .build();

        return Mono.just(ResponseEntity
                .badRequest()
                .body(errorResponse)
        );
    }

    // Handles @Valid DTO binding failures on reactive endpoints
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleValidationExceptions(WebExchangeBindException exception) {

        Map<String, String> fieldsErrors = new HashMap<>(); //
        exception.getFieldErrors().forEach( error ->
            fieldsErrors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Validation failed")
                .errors(fieldsErrors)
                .timestamp(Instant.now())
                .build();

        // Return as mono to avoid blocking the thread
        return Mono.just(ResponseEntity.badRequest().body(errorResponse));
    }

}
