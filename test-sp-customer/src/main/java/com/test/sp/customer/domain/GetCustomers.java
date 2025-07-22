package com.test.sp.customer.domain;

import com.test.sp.customer.domain.enums.GenderEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetCustomers {
    UUID customerId;
    String identification;
    String fullName;
    GenderEnum gender;
    Integer age;
    String address;
    String phone;
    String password;
    Boolean status;
}
