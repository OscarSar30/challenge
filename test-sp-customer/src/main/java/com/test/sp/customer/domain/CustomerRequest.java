package com.test.sp.customer.domain;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequest {
    @NotNull
    @Size(min = 10, max = 13)
    String identification;
    @NotNull
    @Size(min = 10, max = 255)
    String fullName;
    String gender;
    @Min(0) @Max(120)
    Integer age;
    @Size(max = 255)
    String address;
    @Size(max = 20)
    String phone;
    @NotNull
    @Size(min = 8, max = 16)
    String password;
    @NotNull
    Boolean status;
}
