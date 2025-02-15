package com.test.sp.accounts.movements.service.impl;

import com.test.sp.accounts.movements.exception.StatementAccountException;
import com.test.sp.accounts.movements.model.GetStatementAccountByFilterResponse;
import com.test.sp.accounts.movements.repository.MovementRepository;
import com.test.sp.accounts.movements.service.StatementAccountService;
import com.test.sp.accounts.movements.service.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatementAccountServiceImpl implements StatementAccountService {

    private final MovementRepository movementRepository;
    private final MovementMapper movementMapper;

    @Override
    public Mono<Flux<GetStatementAccountByFilterResponse>> getStatementAccountByFilter(String identification,
                                                                                       String dateRange) {
        String[] arrayDate = dateRange.split(";");
        log.info("|-> Starts process of obtain statement account by filters");
        return movementRepository.findStatementAccount(identification,arrayDate[0],arrayDate[1])
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Bank record not found");
                    return Mono.error(new StatementAccountException());
                }))
                .doOnError(throwable -> log.error("Error obtain bank record. Error detail: {}", throwable.getMessage()))
                .collectList()
                .map(Flux::fromIterable);
    }
}
