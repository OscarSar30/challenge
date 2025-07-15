package com.test.sp.customer.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequest {
    String identification;
    String fullName;
    String gender;
    Integer age;
    String address;
    String phone;
    String password;
    Boolean status;
}
