package com.test.sp.accounts.movements.infrastructure.output.repository.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("movements")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovementEntity {
    @Id
    UUID movementId;
    LocalDateTime dateMovement;
    String movementType;
    Double balance;
    Double amount;
    UUID accountId;
}
