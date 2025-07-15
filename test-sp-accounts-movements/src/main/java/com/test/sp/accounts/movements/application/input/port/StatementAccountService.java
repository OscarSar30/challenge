package com.test.sp.accounts.movements.application.input.port;

import com.test.sp.accounts.movements.domain.StatementAccount;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

@Validated
public interface StatementAccountService {

    Flux<StatementAccount> getStatementAccountByFilter(@NotNull @Valid String identification,
                                                       @NotNull @Valid String dateRange);
}
