package com.test.sp.customer.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Generated
public class Person {

    private static Person instance;

    Integer personId;
    String identification;
    String fullName;
    String gender;
    Integer age;
    String address;
    String phone;

    public static Person getInstance() {
        if (instance == null) {
            instance = new Person();
        }
        return instance;
    }
}
