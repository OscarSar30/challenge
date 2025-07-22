package com.test.sp.accounts.movements.infrastructure.input.adapter.rest.impl;

import com.test.sp.accounts.movements.api.StatementAccountsApi;
import com.test.sp.accounts.movements.infrastructure.input.adapter.rest.mapper.AccountMovementsApiMapper;
import com.test.sp.accounts.movements.model.GetStatementAccountByFilterResponse;
import com.test.sp.accounts.movements.application.input.port.StatementAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@AllArgsConstructor
public class StatementAccountControllerImpl implements StatementAccountsApi {

    private final StatementAccountService statementAccountService;
    private final AccountMovementsApiMapper apiMapper;

    @Override
    public Mono<ResponseEntity<Flux<GetStatementAccountByFilterResponse>>> getStatementAccountByFilter(String identification,
                                                                                                       String dateRange,
                                                                                                       ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the query method for statement account.");
        Flux<GetStatementAccountByFilterResponse> response = statementAccountService.getStatementAccountByFilter(identification,dateRange)
                .doOnError(e -> log.error("<-| Error while searching statement account. Error: {}", e.getMessage()))
                .map(apiMapper::toStatementAccountResponse);
        return Mono.just(ResponseEntity.ok().body(response));
    }

}
