package com.test.sp.accounts.movements.repository.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("accounts")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {
    @Id
    public Integer accountId;
    public String accountNumber;
    public String accountType;
    public Double initialBalance;
    public boolean status;
    public Integer customerId;
}
