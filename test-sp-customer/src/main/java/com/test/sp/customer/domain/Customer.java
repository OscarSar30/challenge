package com.test.sp.customer.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@Generated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer extends Person {

    private static Customer instance;

    String customerId;
    String password;
    String status;

    public static Customer getInstance() {
        if (instance == null) {
            instance = new Customer();
        }
        return instance;
    }
}
