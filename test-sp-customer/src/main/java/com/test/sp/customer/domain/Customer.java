package com.test.sp.customer.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("customer")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    public Integer customerId;
    public String password;
    public boolean status;
    public Integer personId;
}
