package com.test.sp.accounts.movements.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetMovements {
    LocalDate dateMovement;
    String accountNumber;
    String movementType;
    Double amount;
    Double balance;
}
