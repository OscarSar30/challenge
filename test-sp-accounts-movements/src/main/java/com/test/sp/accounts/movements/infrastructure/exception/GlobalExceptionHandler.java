package com.test.sp.accounts.movements.infrastructure.exception;

import com.test.sp.accounts.movements.model.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNumberException.class)
    public ResponseEntity<ErrorDTO> handleAccountExistsException(AccountNumberException ex) {
        ErrorDTO errorResponse = new ErrorDTO()
                .code(ex.getCode())
                .description(ex.getDescription());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountIdNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleAccountNotExistsException(AccountIdNotFoundException ex) {
        ErrorDTO errorResponse = new ErrorDTO()
                .code(ex.getCode())
                .description(ex.getDescription());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerExceptionNotFound.class)
    public ResponseEntity<ErrorDTO> handleCustomerException(CustomerExceptionNotFound ex) {
        ErrorDTO errorResponse = new ErrorDTO()
                .code(ex.getCode())
                .description(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovementTypeNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleMovementNotExistsException(MovementTypeNotFoundException ex) {
        ErrorDTO errorResponse = new ErrorDTO()
                .code(ex.getCode())
                .description(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StatementAccountException.class)
    public ResponseEntity<ErrorDTO> handleReportNotGeneratedException(StatementAccountException ex) {
        ErrorDTO errorResponse = new ErrorDTO()
                .code(ex.getCode())
                .description(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountInsufficientBalance.class)
    public ResponseEntity<ErrorDTO> handleAccountInsufficientBalanceException(AccountInsufficientBalance ex) {
        ErrorDTO errorResponse = new ErrorDTO()
                .code(ex.getCode())
                .description(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovementIdNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleMovementIdNotExistsException(MovementIdNotFoundException ex) {
        ErrorDTO errorResponse = new ErrorDTO()
                .code(ex.getCode())
                .description(ex.getDescription());
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
