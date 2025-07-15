package com.test.sp.accounts.movements.application.service;

import com.test.sp.accounts.movements.application.output.port.MovementServiceAdapter;
import com.test.sp.accounts.movements.domain.StatementAccount;
import com.test.sp.accounts.movements.infrastructure.output.repository.MovementRepository;
import com.test.sp.accounts.movements.application.input.port.StatementAccountService;
import com.test.sp.accounts.movements.infrastructure.output.repository.mapper.MovementMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatementAccountServiceImpl implements StatementAccountService {

    private final MovementRepository movementRepository;
    private final MovementServiceAdapter movementServiceAdapter;
    private final MovementMapper movementMapper;

    @Override
    public Flux<StatementAccount> getStatementAccountByFilter(@NotNull @Valid String identification,
                                                              @NotNull @Valid String dateRange) {
        log.info("|-> Starts process of obtain statement account by filters");
        return movementServiceAdapter.findStatementAccountByFilters(identification, dateRange)
                .doOnError(throwable -> log.error("Error obtain bank record. Error detail: {}", throwable.getMessage()));
    }
}
