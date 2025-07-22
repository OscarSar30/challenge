package com.test.sp.accounts.movements.infrastructure.exception;

import com.test.sp.accounts.movements.model.ErrorDetail;
import com.test.sp.accounts.movements.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNumberException.class)
    public ResponseEntity<ErrorResponse> handleAccountExistsException(AccountNumberException ex) {
        ErrorResponse errorResponse = new ErrorResponse()
                .code(ex.getCode())
                .detail(ex.getDetail());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotExistsException(AccountIdNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse()
                .code(ex.getCode())
                .detail(ex.getDetail());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerExceptionNotFound.class)
    public ResponseEntity<ErrorResponse> handleCustomerException(CustomerExceptionNotFound ex) {
        ErrorResponse errorResponse = new ErrorResponse()
                .code(ex.getCode())
                .detail(ex.getDetail());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovementTypeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMovementNotExistsException(MovementTypeNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse()
                .code(ex.getCode())
                .detail(ex.getDetail());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StatementAccountException.class)
    public ResponseEntity<ErrorResponse> handleReportNotGeneratedException(StatementAccountException ex) {
        ErrorResponse errorResponse = new ErrorResponse()
                .code(ex.getCode())
                .detail(ex.getDetail());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountInsufficientBalance.class)
    public ResponseEntity<ErrorResponse> handleAccountInsufficientBalanceException(AccountInsufficientBalance ex) {
        ErrorResponse errorResponse = new ErrorResponse()
                .code(ex.getCode())
                .detail(ex.getDetail());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovementIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMovementIdNotExistsException(MovementIdNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse()
                .code(ex.getCode())
                .detail(ex.getDetail());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>("Invalid data: " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ErrorResponse> handleValidationException(WebExchangeBindException ex) {
        List<ErrorDetail> details = ex.getFieldErrors().stream()
                .map(error -> {
                    ErrorDetail detail = new ErrorDetail();
                    detail.setMessage(error.getField());
                    detail.setBusinessMessage(error.getDefaultMessage());
                    return detail;
                })
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        errorResponse.setDetail(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorResponse.setErrors(details);

        return Mono.just(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .detail(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
