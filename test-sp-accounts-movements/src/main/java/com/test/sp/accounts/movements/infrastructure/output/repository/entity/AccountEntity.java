package com.test.sp.accounts.movements.infrastructure.output.repository.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("accounts")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountEntity {
    @Id
    UUID accountId;
    String accountNumber;
    String accountType;
    Double initialBalance;
    boolean status;
    UUID customerId;
}
