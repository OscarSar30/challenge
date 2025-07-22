package com.test.sp.accounts.movements.domain.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum MovementTypeEnum {
    DEPOSITO("DEPOSITO"),

    RETIRO("RETIRO");

    final String value;

    MovementTypeEnum(String value) {
        this.value = value;
    }
}