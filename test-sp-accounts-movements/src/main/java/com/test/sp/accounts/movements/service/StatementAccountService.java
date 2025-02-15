package com.test.sp.accounts.movements.service;

import com.test.sp.accounts.movements.model.GetStatementAccountByFilterResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StatementAccountService {
    Mono<Flux<GetStatementAccountByFilterResponse>> getStatementAccountByFilter(String identification,
                                                                                String dateRange);
}
