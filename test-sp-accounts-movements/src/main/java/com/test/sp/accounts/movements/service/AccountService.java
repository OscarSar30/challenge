package com.test.sp.accounts.movements.service;

import com.test.sp.accounts.movements.model.GetAccountsResponse;
import com.test.sp.accounts.movements.model.PostAccountRequest;
import com.test.sp.accounts.movements.model.PostAccountResponse;
import com.test.sp.accounts.movements.model.PutAccountByIdRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<PostAccountResponse> postAccount(PostAccountRequest request);

    Mono<Void> putAccountById(Integer accountId, PutAccountByIdRequest request);

    Mono<Flux<GetAccountsResponse>> getAccounts();

    Mono<Void> deleteAccount(Integer accountId);
}
