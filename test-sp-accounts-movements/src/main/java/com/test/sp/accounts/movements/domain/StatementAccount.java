package com.test.sp.accounts.movements.domain;

import com.test.sp.accounts.movements.domain.enums.AccountTypeEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatementAccount {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dateMovement;
    String fullName;
    String accountNumber;
    AccountTypeEnum accountType;
    Double initialBalance;
    Boolean status;
    Double amountMovement;
    Double availableBalance;

}
