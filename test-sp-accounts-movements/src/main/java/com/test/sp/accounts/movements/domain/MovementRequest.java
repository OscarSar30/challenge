package com.test.sp.accounts.movements.domain;

import com.test.sp.accounts.movements.domain.enums.MovementTypeEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    LocalDate dateMovement;
    @NotNull
    MovementTypeEnum movementType;
    @NotNull
    @DecimalMin("0.01")
    Double amount;
    Double balance;
    @NotNull
    UUID accountId;

}
