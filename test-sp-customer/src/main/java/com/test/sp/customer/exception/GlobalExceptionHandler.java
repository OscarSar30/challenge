package com.test.sp.customer.exception;

import com.test.sp.customer.model.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>("Datos inválidos: " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneralException(Exception ex) {
        ErrorDTO errorResponse = new ErrorDTO()
                .code("500")
                .description("An unexpected error occurred.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
