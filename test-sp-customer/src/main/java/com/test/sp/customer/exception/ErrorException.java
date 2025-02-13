package com.test.sp.customer.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorException extends Exception{

    final String code;
    final String description;

    public ErrorException(final String code, final String description) {
        super(description);
        this.code = code;
        this.description = description;
    }

}
