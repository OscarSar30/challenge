package com.test.sp.accounts.movements.service.impl;

import com.test.sp.accounts.movements.domain.Account;
import com.test.sp.accounts.movements.exception.AccountIdNotFound;
import com.test.sp.accounts.movements.exception.AccountNumberException;
import com.test.sp.accounts.movements.model.GetAccountsResponse;
import com.test.sp.accounts.movements.model.PostAccountRequest;
import com.test.sp.accounts.movements.model.PostAccountResponse;
import com.test.sp.accounts.movements.model.PutAccountByIdRequest;
import com.test.sp.accounts.movements.repository.AccountRepository;
import com.test.sp.accounts.movements.service.AccountService;
import com.test.sp.accounts.movements.service.CustomerService;
import com.test.sp.accounts.movements.service.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountMapper accountMapper;

    @Override
    public Mono<PostAccountResponse> postAccount(PostAccountRequest request) {
        log.info("|-> Starts process of creating new account service");
        return customerService.verifyCustomer(request.getCustomerId())
                .flatMap(getCustomersResponse -> {
                    log.info("|-> Customer obtained from DB");
                    return accountRepository.findByAccountNumber(request.getAccountNumber())
                            .switchIfEmpty(Mono.just(new Account()))
                            .flatMap(account -> {
                                if (account.getAccountNumber() != null) {
                                    log.error("|-> Error. Account number already exists in DB: {}", request.getAccountNumber());
                                    return Mono.error(new AccountNumberException());
                                } else {
                                    log.info("|-> Account number validated successfully");
                                    return accountRepository.save(accountMapper.postRequestClientToAccountEntity(request))
                                            .doOnError(e -> log.error("Error creating account: {}", e.getMessage()))
                                            .doOnTerminate(() -> log.info("|-> Finished processing account creation"))
                                            .map(entity -> {
                                                log.info("|-> Account created with id: {}", entity.getAccountId());
                                                PostAccountResponse response = new PostAccountResponse();
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
    public Mono<Void> putAccountById(Integer accountId,
                                     PutAccountByIdRequest request) {
        log.info("|-> Starts process of updating account by ID {}", accountId);
        return accountRepository.findById(accountId)
                .flatMap(account -> {
                    log.info("|-> Account exists in the system.");
                    return accountRepository.save(accountMapper.putRequestClientToAccount(request, account));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Customer not found with ID {}",accountId);
                    return Mono.error(new AccountIdNotFound());
                }))
                .then();
    }

    @Override
    public Mono<Flux<GetAccountsResponse>> getAccounts() {
        log.info("|-> Starts process of search accounts");
        return accountRepository.findAll()
                .flatMap(account ->
                        customerService.verifyCustomer(account.customerId)
                                .map(customer -> accountMapper.getAccountAll(account, customer)))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Accounts not found");
                    return Mono.error(new AccountIdNotFound());
                }))
                .collectList()
                .map(Flux::fromIterable);
    }

    @Override
    public Mono<Void> deleteAccount(Integer accountId) {
        log.info("|-> Starts process of deleting account by ID {}", accountId);
        return accountRepository.findById(accountId)
                .flatMap(customer -> {
                    log.info("|-> Account exists in the system.");
                    return accountRepository.deleteById(accountId);
                })
                .doOnSuccess(v -> log.info("|-> Account was deleted."))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Account not found with ID {}", accountId);
                    return Mono.error(new AccountIdNotFound());
                }))
                .then();
    }
}
