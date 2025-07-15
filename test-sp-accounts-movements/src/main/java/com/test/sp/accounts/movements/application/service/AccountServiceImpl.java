package com.test.sp.accounts.movements.application.service;

import com.test.sp.accounts.movements.application.output.port.AccountServiceAdapter;
import com.test.sp.accounts.movements.application.output.port.CustomerServiceAdapter;
import com.test.sp.accounts.movements.domain.AccountRequest;
import com.test.sp.accounts.movements.domain.AccountResponse;
import com.test.sp.accounts.movements.domain.GetAccounts;
import com.test.sp.accounts.movements.infrastructure.exception.AccountNumberException;
import com.test.sp.accounts.movements.application.input.port.AccountService;
import com.test.sp.accounts.movements.infrastructure.output.repository.mapper.AccountMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountServiceAdapter accountServiceAdapter;
    private final CustomerServiceAdapter customerServiceAdapter;
    private final AccountMapper accountMapper;

    @Override
    public Mono<AccountResponse> postAccount(@NotNull @Valid AccountRequest request) {
        log.info("|-> Starts process of creating new account service");
        return customerServiceAdapter.verifyCustomer(request.getCustomerId())
                .flatMap(getCustomersResponse -> {
                    log.info("|-> Customer obtained from DB");
                    return accountServiceAdapter.verifyAccountNumber(request.getAccountNumber())
                            .flatMap(account -> {
                                if (account.getAccountNumber() != null) {
                                    log.error("|-> Error. Account number already exists in DB: {}", request.getAccountNumber());
                                    return Mono.error(new AccountNumberException());
                                } else {
                                    log.info("|-> Account number validated successfully");
                                    return accountServiceAdapter.saveAccount(request)
                                            .map(entity -> {
                                                log.info("|-> Account created with id: {}", entity.getAccountId());
                                                AccountResponse response = new AccountResponse();
                                                response.setAccountId(entity.getAccountId());
                                                return response;
                                            });
                                }
                            });
                }).doOnError(throwable -> log.error(
                        "|-> Error obtain create new account. Error detail {}", throwable.getMessage()
                ));
    }

    @Override
    public Mono<Void> putAccountById(@NotNull @NotBlank UUID accountId,
                                     @NotNull @Valid AccountRequest request) {
        log.info("|-> Starts process of updating account by ID {}", accountId);
        return accountServiceAdapter.findByAccountId(accountId)
                .flatMap(account -> {
                    log.info("|-> Account id exists in the system.");
                    return accountServiceAdapter.updateAccount(request, account);
                }).doOnError(throwable -> log.error(
                        "|-> Error update account. Error detail {}", throwable.getMessage()
                ))
                .then();
    }

    @Override
    public Flux<GetAccounts> getAccounts() {
        log.info("|-> Starts process of search accounts");
        return accountServiceAdapter.findAllAccounts()
                .flatMap(account ->
                        customerServiceAdapter.verifyCustomer(account.getCustomerId())
                                .map(customer ->
                                        accountMapper.getAccountAll(account, customer)))
                .doOnError(throwable -> log.error(
                        "|-> Error search all accounts. Error detail {}", throwable.getMessage()
                ));
    }

    @Override
    public Mono<Void> deleteAccount(@NotNull @NotBlank UUID accountId) {
        log.info("|-> Starts process of deleting account by ID {}", accountId);
        return accountServiceAdapter.findByAccountId(accountId)
                .flatMap(account -> {
                    log.info("|-> Account exists in the system.");
                    return accountServiceAdapter.deleteByAccountId(accountId);
                }).doOnError(throwable -> log.error(
                        "|-> Error delete account. Error detail {}", throwable.getMessage()
                ))
                .doOnSuccess(v -> log.info("|-> Account was deleted."))
                .then();
    }
}
