package com.test.sp.accounts.movements.domain.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum AccountTypeEnum {
    AHORRO("AHORRO"),

    CORRIENTE("CORRIENTE");

    final String value;

    AccountTypeEnum(String value) {
        this.value = value;
    }
}