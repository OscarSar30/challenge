package com.test.sp.accounts.movements.domain;

import com.test.sp.accounts.movements.domain.enums.AccountTypeEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRequest {

    @NotNull
    @Size(min = 10, max = 20)
    String accountNumber;
    @NotNull
    AccountTypeEnum accountType;
    @NotNull
    @DecimalMin("0")
    Double initialBalance;
    @NotNull
    Boolean status;
    UUID customerId;

}
