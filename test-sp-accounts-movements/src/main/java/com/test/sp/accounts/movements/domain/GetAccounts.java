package com.test.sp.accounts.movements.domain;

import com.test.sp.accounts.movements.domain.enums.AccountTypeEnum;
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
    AccountTypeEnum accountType;
    String initialBalance;
    Boolean status;
    String fullName;
}
