package com.test.sp.accounts.movements.repository.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("movements")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementEntity {
    @Id
    public Integer movementId;
    public LocalDate dateMovement;
    public String movementType;
    public Double balance;
    public Double amount;
    public Integer accountId;
}
