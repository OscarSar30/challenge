package com.test.sp.accounts.movements.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovementRequest {

    LocalDate dateMovement;
    String movementType;
    Double amount;
    Double balance;
    UUID accountId;

}
