package com.test.sp.accounts.movements.infrastructure.output.adapter;

import com.test.sp.accounts.movements.application.output.port.MovementServiceAdapter;
import com.test.sp.accounts.movements.domain.MovementRequest;
import com.test.sp.accounts.movements.domain.StatementAccount;
import com.test.sp.accounts.movements.infrastructure.exception.MovementIdNotFoundException;
import com.test.sp.accounts.movements.infrastructure.exception.StatementAccountException;
import com.test.sp.accounts.movements.infrastructure.output.repository.MovementRepository;
import com.test.sp.accounts.movements.infrastructure.output.repository.entity.MovementEntity;
import com.test.sp.accounts.movements.infrastructure.output.repository.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovementServiceAdapterImpl implements MovementServiceAdapter {

    private final MovementRepository movementRepository;
    private final MovementMapper movementMapper;

    @Override
    public Mono<MovementEntity> findMovementByAccountId(UUID accountId) {
        log.info("|-> Initiate search movement by account id adapter.");
        return movementRepository.findByAccountId(accountId)
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Movement not found by account id {}: ", accountId);
                    return Mono.just(new MovementEntity());
                }))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error search movement by account id. Error detail {}", error.getMessage()));
    }

    @Override
    public Mono<MovementEntity> saveMovement(MovementRequest request) {
        log.info("|-> Initiate save movement adapter.");
        return movementRepository.save(movementMapper.requestToMovementEntity(request))
                .doOnSuccess(customersEntity -> log.info(
                        "|-> Adapter:: Creation movement successfully."))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error create movement. Error detail {}", error.getMessage()));
    }

    @Override
    public Flux<MovementEntity> findAllMovements() {
        log.info("|-> Initiate search all movements adapter.");
        return movementRepository.findAll()
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Movements not found");
                    return Mono.error(new MovementIdNotFoundException());
                }))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error search movements. Error detail {}", error.getMessage()));
    }

    @Override
    public Flux<StatementAccount> findStatementAccountByFilters(String identification,
                                                                String dateRange) {
        String[] arrayDate = dateRange.split(";");
        log.info("|-> Initiate search statement account by filters adapter.");
        return movementRepository.findStatementAccount(identification, arrayDate[0], arrayDate[1])
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Statement account not found");
                    return Mono.error(new StatementAccountException());
                }))
                .doOnTerminate(() -> log.info(
                        "|-> Adapter:: Search data statement account by filters successfully."))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error search statement account by filters. Error detail {}", error.getMessage()));
    }
}
