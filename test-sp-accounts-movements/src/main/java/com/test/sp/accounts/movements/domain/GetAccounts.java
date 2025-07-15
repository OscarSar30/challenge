package com.test.sp.accounts.movements.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetAccounts {
    UUID accountId;
    String accountNumber;
    String accountType;
    String initialBalance;
    Boolean status;
    String fullName;
}
