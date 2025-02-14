package com.test.sp.accounts.movements.exception;

import com.test.sp.accounts.movements.model.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNumberException.class)
    public ResponseEntity<ErrorDTO> handleCustomerException(AccountNumberException ex) {
        ErrorDTO errorResponse = new ErrorDTO()
                .code(ex.getCode())
                .description(ex.getDescription());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountIdNotFound.class)
    public ResponseEntity<ErrorDTO> handleCustomerException(AccountIdNotFound ex) {
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneralException(Exception ex) {
        ErrorDTO errorResponse = new ErrorDTO()
                .code("500")
                .description("An unexpected error occurred.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
