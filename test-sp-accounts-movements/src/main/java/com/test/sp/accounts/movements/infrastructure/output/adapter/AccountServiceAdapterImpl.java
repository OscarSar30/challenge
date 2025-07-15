package com.test.sp.accounts.movements.infrastructure.output.adapter;

import com.test.sp.accounts.movements.application.output.port.AccountServiceAdapter;
import com.test.sp.accounts.movements.domain.AccountRequest;
import com.test.sp.accounts.movements.infrastructure.exception.AccountIdNotFoundException;
import com.test.sp.accounts.movements.infrastructure.output.repository.AccountRepository;
import com.test.sp.accounts.movements.infrastructure.output.repository.entity.AccountEntity;
import com.test.sp.accounts.movements.infrastructure.output.repository.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceAdapterImpl implements AccountServiceAdapter {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public Mono<AccountEntity> verifyAccountNumber(String accountNumber) {
        log.info("|-> Initiate search account by account number adapter.");
        return accountRepository.findByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Data account {} not found.", accountNumber);
                    return Mono.just(new AccountEntity());
                }))
                .doOnSuccess(customersEntity -> log.info(
                        "|-> Adapter:: Search data account by account number successfully."))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error search account by account number. Error detail {}", error.getMessage()));
    }

    @Override
    public Mono<AccountEntity> saveAccount(AccountRequest request) {
        log.info("|-> Initiate save account adapter.");
        return accountRepository.save(accountMapper.requestToAccountEntity(request))
                .doOnSuccess(accountEntity -> log.info(
                        "|-> Adapter:: Creation account successfully."))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error create account. Error detail {}", error.getMessage()));
    }

    @Override
    public Mono<AccountEntity> findByAccountId(UUID accountId) {
        log.info("|-> Initiate search account by id adapter.");
        return accountRepository.findById(accountId)
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Account not found with ID {}", accountId);
                    return Mono.error(new AccountIdNotFoundException());
                }))
                .doOnSuccess(customersEntity -> log.info(
                        "|-> Adapter:: Search data account by id successfully."))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error search account. Error detail {}", error.getMessage()));
    }

    @Override
    public Mono<AccountEntity> updateAccount(AccountRequest request,
                                             AccountEntity account) {
        log.info("|-> Initiate update account adapter.");
        return accountRepository.save(accountMapper.requestToAccountEntity(request, account))
                .doOnSuccess(entity -> log.info(
                        "|-> Adapter:: Update account successfully."))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error update account by ID {}. Error detail {}", account.getAccountId(),
                                error.getMessage()));
    }

    @Override
    public Flux<AccountEntity> findAllAccounts() {
        log.info("|-> Initiate search all accounts adapter.");
        return accountRepository.findAll()
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Accounts not found");
                    return Mono.error(new AccountIdNotFoundException());
                }))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error search accounts. Error detail {}", error.getMessage()));
    }

    @Override
    public Mono<Void> deleteByAccountId(UUID accountId) {
        log.info("|-> Initiate delete account by id adapter.");
        return accountRepository.deleteById(accountId)
                .doOnSuccess(unused -> log.info(
                        "Adapter:: Delete data account successfully"))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error delete account. Error detail {}", error.getMessage()));
    }
}
