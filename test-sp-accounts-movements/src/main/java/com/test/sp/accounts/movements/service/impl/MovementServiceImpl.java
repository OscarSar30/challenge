package com.test.sp.accounts.movements.service.impl;

import com.test.sp.accounts.movements.domain.Movement;
import com.test.sp.accounts.movements.exception.AccountIdNotFoundException;
import com.test.sp.accounts.movements.exception.InsufficientBalanceException;
import com.test.sp.accounts.movements.exception.MovementTypeNotFoundException;
import com.test.sp.accounts.movements.model.*;
import com.test.sp.accounts.movements.repository.AccountRepository;
import com.test.sp.accounts.movements.repository.MovementRepository;
import com.test.sp.accounts.movements.service.MovementService;
import com.test.sp.accounts.movements.service.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;
    private final MovementMapper movementMapper;
    private static final String MOVEMENT_TYPE_DEPOSIT = "Deposito";
    private static final String MOVEMENT_TYPE_WITHDRAWAL = "Retiro";

    @Override
    public Mono<PostMovementResponse> postMovement(PostMovementRequest request) {
        log.info("|-> Starts process of creating new movement service");
        return accountRepository.findByAccountId(request.getAccountId())
                .flatMap(account -> {
                    if (account == null || account.getInitialBalance() < 0) {
                        log.error("|-> Account not found or invalid balance");
                        return Mono.error(new AccountIdNotFoundException());
                    }
                    return movementRepository.findByAccountId(request.getAccountId())
                            .switchIfEmpty(Mono.just(new Movement()))
                            .flatMap(movement -> {
                                log.info("|-> Last movement obtained from DB. Initial process movement DEPOSITO");
                                if (request.getMovementType().equals(MOVEMENT_TYPE_DEPOSIT)) {
                                    request.setBalance(movement.getBalance() == null
                                            ? account.getInitialBalance() + request.getAmount()
                                            : movement.getBalance() + request.getAmount());
                                    return saveMovement(request);
                                }
                                else if (request.getMovementType().equals(MOVEMENT_TYPE_WITHDRAWAL)) {
                                    log.info("|-> Last movement obtained from DB. Initial process movement RETIRO");
                                    if (account.getInitialBalance() < request.getAmount()
                                            && (movement.getBalance() == null || movement.getBalance() < request.getAmount())) {
                                        log.error("|-> Insufficient balance for RETIRO");
                                        return Mono.error(new InsufficientBalanceException());
                                    }
                                    request.setBalance(movement.getBalance() == null
                                            ? account.getInitialBalance() - request.getAmount()
                                            : movement.getBalance() - request.getAmount());
                                    request.setAmount(-request.getAmount());
                                    return saveMovement(request);
                                } else {
                                    log.error("|-> Movement type not recognized");
                                    return Mono.error(new MovementTypeNotFoundException());
                                }
                            }).doOnError(throwable -> log.error("Error obtain last movement. Error detail: {}", throwable.getMessage()));
                }).doOnError(throwable -> log.error("Error obtain movement. Error detail: {}", throwable.getMessage()));
    }

    @Override
    public Mono<Flux<GetMovementsResponse>> getMovements() {
        log.info("|-> Starts process of search movements");
        return movementRepository.findAll()
                .flatMap(movement ->
                        accountRepository.findByAccountId(movement.accountId)
                                .map(account -> movementMapper.getMovementAll(movement, account)))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Movements not found");
                    return Mono.error(new AccountIdNotFoundException());
                }))
                .collectList()
                .map(Flux::fromIterable);
    }

    private Mono<PostMovementResponse> saveMovement(PostMovementRequest request) {
        return movementRepository.save(movementMapper.postRequestClientToMovementEntity(request))
                .map(entity -> {
                    log.info("|-> Movement created. Type: {} with amount {}", request.getMovementType(), request.getAmount());
                    PostMovementResponse response = new PostMovementResponse();
                    response.setMovementId(entity.getMovementId());
                    return response;
                });
    }
}
