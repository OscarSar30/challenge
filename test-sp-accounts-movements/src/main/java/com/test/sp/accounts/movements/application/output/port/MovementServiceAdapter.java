package com.test.sp.accounts.movements.application.output.port;

import com.test.sp.accounts.movements.domain.MovementRequest;
import com.test.sp.accounts.movements.domain.StatementAccount;
import com.test.sp.accounts.movements.infrastructure.output.repository.entity.MovementEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MovementServiceAdapter {

    Mono<MovementEntity> findMovementByAccountId(UUID accountId);

    Mono<MovementEntity> saveMovement(MovementRequest request);

    Flux<MovementEntity> findAllMovements();

    Flux<StatementAccount> findStatementAccountByFilters(@NotNull @Valid String identification,
                                                         @NotNull @Valid String dateRange);
}
