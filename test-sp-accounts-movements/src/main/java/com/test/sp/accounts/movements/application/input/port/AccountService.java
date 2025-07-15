package com.test.sp.accounts.movements.application.input.port;

import com.test.sp.accounts.movements.domain.AccountRequest;
import com.test.sp.accounts.movements.domain.AccountResponse;
import com.test.sp.accounts.movements.domain.GetAccounts;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Validated
public interface AccountService {

    Mono<AccountResponse> postAccount(@NotNull @Valid AccountRequest request);

    Mono<Void> putAccountById(@NotNull @NotBlank UUID accountId,
                              @NotNull @Valid AccountRequest request);

    Flux<GetAccounts> getAccounts();

    Mono<Void> deleteAccount(@NotNull @NotBlank UUID accountId);
}
