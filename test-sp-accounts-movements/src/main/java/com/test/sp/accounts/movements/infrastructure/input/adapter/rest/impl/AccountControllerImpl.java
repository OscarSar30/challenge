package com.test.sp.accounts.movements.infrastructure.input.adapter.rest.impl;

import com.test.sp.accounts.movements.api.AccountsApi;
import com.test.sp.accounts.movements.infrastructure.input.adapter.rest.mapper.AccountMovementsApiMapper;
import com.test.sp.accounts.movements.model.GetAccountsResponse;
import com.test.sp.accounts.movements.model.PostAccountRequest;
import com.test.sp.accounts.movements.model.PostAccountResponse;
import com.test.sp.accounts.movements.model.PutAccountByIdRequest;
import com.test.sp.accounts.movements.application.input.port.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@Slf4j
@AllArgsConstructor
public class AccountControllerImpl implements AccountsApi {

    private final AccountService accountService;
    private final AccountMovementsApiMapper apiMapper;

    @Override
    public Mono<ResponseEntity<PostAccountResponse>> postAccount(Mono<PostAccountRequest> postAccountRequest,
                                                                 ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the account create method.");
        return postAccountRequest
                .map(apiMapper::toAccountRequest)
                .flatMap(accountService::postAccount)
                .doOnError(e -> log.error("<-| Error while creating account. Error: {}", e.getMessage()))
                .map(apiMapper::toAccountResponse)
                .map(postAccountResponse ->
                        new ResponseEntity<>(postAccountResponse, HttpStatus.CREATED));
    }

    @Override
    public Mono<ResponseEntity<Void>> putAccountById(UUID accountId,
                                                     Mono<PutAccountByIdRequest> putAccountByIdRequest,
                                                     ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the account update method.");
        return putAccountByIdRequest
                .map(apiMapper::toAccountRequest)
                .flatMap(request ->
                        accountService.putAccountById(accountId, request))
                .doOnError(e -> log.error("<-| Error while updating account. Error: {}", e.getMessage()))
                .thenReturn(ResponseEntity.ok().build());

    }

    @Override
    public Mono<ResponseEntity<Flux<GetAccountsResponse>>> getAccounts(ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the query method for all accounts.");
        Flux<GetAccountsResponse> response =
                accountService.getAccounts()
                        .doOnError(e -> log.error("<-| Error while searching accounts. Error: {}", e.getMessage()))
                .map(apiMapper::toGetAccountsResponse);
        return Mono.just(ResponseEntity.ok().body(response));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteAccount(UUID accountId,
                                                    ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the customer delete method.");
        return accountService.deleteAccount(accountId)
                .doOnError(e -> log.error("<-| Error while deleting account. Error: {}", e.getMessage()))
                .thenReturn(ResponseEntity.ok().build());
    }
}
