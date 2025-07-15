package com.test.sp.customer.infrastructure.output.repository.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("person")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonEntity {
    @Id
    UUID personId;
    String fullName;
    String gender;
    Integer age;
    String identification;
    String address;
    String phone;
}
