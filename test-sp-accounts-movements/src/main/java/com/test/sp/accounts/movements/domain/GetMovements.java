package com.test.sp.accounts.movements.domain;

import com.test.sp.accounts.movements.domain.enums.MovementTypeEnum;
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
    MovementTypeEnum movementType;
    Double amount;
    Double balance;
}
