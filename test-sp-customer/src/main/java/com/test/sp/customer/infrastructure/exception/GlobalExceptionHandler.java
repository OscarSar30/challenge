package com.test.sp.customer.infrastructure.exception;

import com.test.sp.customer.model.ErrorDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PostCustomerException.class)
    public ResponseEntity<ErrorDTO> handleCustomerException(PostCustomerException ex) {
        ErrorDTO errorResponse = new ErrorDTO()
                .code(ex.getCode())
                .description(ex.getDescription());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerExceptionNotFound.class)
    public ResponseEntity<ErrorDTO> handleCustomerNotFoundException(CustomerExceptionNotFound ex) {
        ErrorDTO errorResponse = new ErrorDTO()
                .code(ex.getCode())
                .description(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PersonExceptionNotFound.class)
    public ResponseEntity<ErrorDTO> handlePersonNotFoundException(PersonExceptionNotFound ex) {
        ErrorDTO errorResponse = new ErrorDTO()
                .code(ex.getCode())
                .description(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>("Invalid data: " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Map<String, Object>> handleValidationException(WebExchangeBindException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> fieldErrors = ex.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage,
                        (existing, replacement) -> existing
                ));
        response.put("code", HttpStatus.BAD_REQUEST.value());
        response.put("description", HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.put("errors", fieldErrors);

        return Mono.just(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneralException(Exception ex) {
        ErrorDTO errorResponse = new ErrorDTO()
                .code("500")
                .description("An unexpected error occurred.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
