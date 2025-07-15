package com.test.sp.accounts.movements.infrastructure.input.adapter.rest.impl;

import com.test.sp.accounts.movements.api.StatementAccountsApi;
import com.test.sp.accounts.movements.model.GetStatementAccountByFilterResponse;
import com.test.sp.accounts.movements.application.input.port.StatementAccountService;
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
public class StatementAccountControllerImpl implements StatementAccountsApi {

    private final StatementAccountService statementAccountService;

    @Override
    public Mono<ResponseEntity<Flux<GetStatementAccountByFilterResponse>>> getStatementAccountByFilter(String identification,
                                                                                                       String dateRange,
                                                                                                       ServerWebExchange exchange) {
        return statementAccountService.getStatementAccountByFilter(identification,dateRange)
                .map(response ->
                        new ResponseEntity<>(response, HttpStatus.OK));
    }

}
