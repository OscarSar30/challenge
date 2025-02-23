package com.test.sp.customer.repository.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("person")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity {
    @Id
    public Integer personId;
    public String fullName;
    public String gender;
    public Integer age;
    public String identification;
    public String address;
    public String phone;
}
