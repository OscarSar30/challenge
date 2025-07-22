package com.test.sp.customer.domain.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum GenderEnum {
    MASCULINO("MASCULINO"),

    FEMENINO("FEMENINO"),

    OTRO("OTRO");

    final String value;

    GenderEnum(String value) {
        this.value = value;
    }
}
