package com.test.sp.accounts.movements.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Date;

@Table("movements")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movement {
    @Id
    public Integer movementId;
    public LocalDate dateMovement;
    public String movementType;
    public Double balance;
    public Double amount;
    public Integer accountId;
}
