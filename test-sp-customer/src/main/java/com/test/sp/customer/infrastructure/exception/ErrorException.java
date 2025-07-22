package com.test.sp.customer.infrastructure.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorException extends Exception{

    final String code;
    final String detail;

    public ErrorException(final String code, final String detail) {
        super(detail);
        this.code = code;
        this.detail = detail;
    }

}
