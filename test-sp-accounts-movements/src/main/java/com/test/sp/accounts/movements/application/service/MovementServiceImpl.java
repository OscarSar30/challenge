package com.test.sp.accounts.movements.application.service;

import com.test.sp.accounts.movements.infrastructure.output.repository.entity.AccountEntity;
import com.test.sp.accounts.movements.infrastructure.output.repository.entity.MovementEntity;
import com.test.sp.accounts.movements.infrastructure.exception.AccountIdNotFoundException;
import com.test.sp.accounts.movements.infrastructure.exception.AccountInsufficientBalance;
import com.test.sp.accounts.movements.infrastructure.exception.MovementTypeNotFoundException;
import com.test.sp.accounts.movements.model.*;
import com.test.sp.accounts.movements.infrastructure.output.repository.AccountRepository;
import com.test.sp.accounts.movements.infrastructure.output.repository.MovementRepository;
import com.test.sp.accounts.movements.application.input.port.MovementService;
import com.test.sp.accounts.movements.infrastructure.output.repository.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.test.sp.accounts.movements.util.Constants.MOVEMENT_TYPE_DEPOSIT;
import static com.test.sp.accounts.movements.util.Constants.MOVEMENT_TYPE_WITHDRAWAL;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;
    private final MovementMapper movementMapper;


    @Override
    public Mono<PostMovementResponse> postMovement(PostMovementRequest request) {
        log.info("|-> Starts process of creating new movement service");
        return accountRepository.findByAccountId(request.getAccountId())
                .flatMap(account -> handleAccount(account, request))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Account not found with ID {}", request.getAccountId());
                    return Mono.error(new AccountIdNotFoundException());
                }))
                .doOnError(throwable -> log.error("Error obtaining movement. Error detail: {}", throwable.getMessage()));
    }

    @Override
    public Mono<Flux<GetMovementsResponse>> getMovements() {
        log.info("|-> Starts process of search movements");
        return movementRepository.findAll()
                .flatMap(movementEntity ->
                        accountRepository.findByAccountId(movementEntity.accountId)
                                .map(account -> movementMapper.getMovementAll(movementEntity, account)))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Movements not found");
                    return Mono.error(new AccountIdNotFoundException());
                }))
                .collectList()
                .map(Flux::fromIterable);
    }

    private Mono<PostMovementResponse> handleAccount(AccountEntity accountEntity, PostMovementRequest request) {
        if (accountEntity.getInitialBalance() < 0) {
            log.error("|-> Account ID {} with insufficient balance.", request.getAccountId());
            return Mono.error(new AccountIdNotFoundException());
        }
        return movementRepository.findByAccountId(request.getAccountId())
                .switchIfEmpty(Mono.just(new MovementEntity()))
                .flatMap(movementEntity -> processMovement(accountEntity, movementEntity, request));
    }

    private Mono<PostMovementResponse> processMovement(AccountEntity accountEntity, MovementEntity movementEntity, PostMovementRequest request) {
        log.info("|-> Last movement obtained from DB. Initial process movement {}", request.getMovementType());
        return switch (request.getMovementType().toUpperCase()) {
            case MOVEMENT_TYPE_DEPOSIT -> handleDeposit(accountEntity, movementEntity, request);
            case MOVEMENT_TYPE_WITHDRAWAL -> handleWithdrawal(accountEntity, movementEntity, request);
            default -> {
                log.error("|-> Movement type not recognized");
                yield Mono.error(new MovementTypeNotFoundException());
            }
        };
    }

    private Mono<PostMovementResponse> handleDeposit(AccountEntity accountEntity, MovementEntity movementEntity, PostMovementRequest request) {
        double newBalance = (movementEntity.getBalance() == null)
                ? accountEntity.getInitialBalance() + request.getAmount()
                : movementEntity.getBalance() + request.getAmount();
        request.setBalance(newBalance);
        return saveMovement(request);
    }

    private Mono<PostMovementResponse> handleWithdrawal(AccountEntity accountEntity, MovementEntity movementEntity, PostMovementRequest request) {
        double currentBalance = (movementEntity.getBalance() == null) ? accountEntity.getInitialBalance() : movementEntity.getBalance();
        if (currentBalance < request.getAmount()) {
            log.error("|-> Insufficient balance for RETIRO");
            return Mono.error(new AccountInsufficientBalance());
        }
        double newBalance = currentBalance - request.getAmount();
        request.setBalance(newBalance);
        request.setAmount(-request.getAmount());
        return saveMovement(request);
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
