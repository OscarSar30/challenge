package com.test.sp.accounts.movements.application.output.port;

import com.test.sp.accounts.movements.domain.AccountRequest;
import com.test.sp.accounts.movements.infrastructure.output.repository.entity.AccountEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AccountServiceAdapter {

    Mono<AccountEntity> verifyAccountNumber(@NotNull @NotBlank String accountNumber);

    Mono<AccountEntity> saveAccount(@NotNull @Valid AccountRequest request);

    Mono<AccountEntity> findByAccountId(@NotNull @NotBlank UUID accountId);

    Mono<AccountEntity> updateAccount(@NotNull @Valid AccountRequest request,
                                      AccountEntity account);

    Flux<AccountEntity> findAllAccounts();

    Mono<Void> deleteByAccountId(@NotNull @NotBlank UUID accountId);
}
