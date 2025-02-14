package com.test.sp.accounts.movements.controller;

import com.test.sp.accounts.movements.api.AccountsApi;
import com.test.sp.accounts.movements.model.GetAccountsResponse;
import com.test.sp.accounts.movements.model.PostAccountRequest;
import com.test.sp.accounts.movements.model.PostAccountResponse;
import com.test.sp.accounts.movements.model.PutAccountByIdRequest;
import com.test.sp.accounts.movements.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@AllArgsConstructor
public class AccountControllerImpl implements AccountsApi {

    private final AccountService accountService;

    @Override
    public Mono<ResponseEntity<PostAccountResponse>> postAccount(Mono<PostAccountRequest> postAccountRequest,
                                                                 ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the account create method.");
        return postAccountRequest
                .flatMap(accountService::postAccount)
                .map(postCustomerResponse ->
                        new ResponseEntity<>(postCustomerResponse, HttpStatus.CREATED));
    }

    @Override
    public Mono<ResponseEntity<Void>> putAccountById(Integer accountId,
                                                     Mono<PutAccountByIdRequest> putAccountByIdRequest,
                                                     ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the account update method.");
        return putAccountByIdRequest
                .flatMap(request ->
                        accountService.putAccountById(accountId, request))
                .map(s -> new ResponseEntity<>(HttpStatus.OK));

    }

    @Override
    public Mono<ResponseEntity<Flux<GetAccountsResponse>>> getAccounts(ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the query method for all accounts.");
        return accountService.getAccounts()
                .map(getAccountsResponse ->
                        new ResponseEntity<>(getAccountsResponse, HttpStatus.OK));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteAccount(Integer accountId,
                                                    ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the customer delete method.");
        return accountService.deleteAccount(accountId)
                .map(s -> new ResponseEntity<>(HttpStatus.OK));
    }
}
