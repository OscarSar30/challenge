package com.test.sp.accounts.movements.application.service;

import com.test.sp.accounts.movements.application.output.port.AccountServiceAdapter;
import com.test.sp.accounts.movements.application.output.port.MovementServiceAdapter;
import com.test.sp.accounts.movements.domain.GetMovements;
import com.test.sp.accounts.movements.domain.MovementRequest;
import com.test.sp.accounts.movements.domain.MovementResponse;
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

    private final AccountServiceAdapter accountServiceAdapter;
    private final MovementServiceAdapter movementServiceAdapter;
    private final MovementMapper movementMapper;


    @Override
    public Mono<MovementResponse> postMovement(MovementRequest request) {
        log.info("|-> Starts process of creating new movement service");
        return accountServiceAdapter.findByAccountId(request.getAccountId())
                .flatMap(account -> handleAccount(account, request))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Account not found with ID {}", request.getAccountId());
                    return Mono.error(new AccountIdNotFoundException());
                }))
                .doOnError(throwable -> log.error("Error obtaining movement. Error detail: {}", throwable.getMessage()));
    }

    @Override
    public Flux<GetMovements> getMovements() {
        log.info("|-> Starts process of search movements");
        return movementServiceAdapter.findAllMovements()
                .flatMap(movementEntity ->
                        accountServiceAdapter.findByAccountId(movementEntity.getAccountId())
                                .map(account -> movementMapper.getMovementAll(movementEntity, account)))
                .doOnError(throwable -> log.error(
                        "|-> Error search all customers. Error detail {}", throwable.getMessage()
                ));
    }

    private Mono<MovementResponse> handleAccount(AccountEntity accountEntity,
                                                     MovementRequest request) {
        if (accountEntity.getInitialBalance() < 0) {
            log.error("|-> Account ID {} with insufficient balance.", request.getAccountId());
            return Mono.error(new AccountIdNotFoundException());
        }
        return movementServiceAdapter.findMovementByAccountId(request.getAccountId())
                .flatMap(movementEntity ->
                        processMovement(accountEntity, movementEntity, request));
    }

    private Mono<MovementResponse> processMovement(AccountEntity accountEntity,
                                                       MovementEntity movementEntity,
                                                       MovementRequest request) {
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

    private Mono<MovementResponse> handleDeposit(AccountEntity accountEntity,
                                                 MovementEntity movementEntity,
                                                 MovementRequest request) {
        double newBalance = (movementEntity.getBalance() == null)
                ? accountEntity.getInitialBalance() + request.getAmount()
                : movementEntity.getBalance() + request.getAmount();
        request.setBalance(newBalance);
        return saveMovement(request);
    }

    private Mono<MovementResponse> handleWithdrawal(AccountEntity accountEntity, MovementEntity movementEntity, MovementRequest request) {
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

    private Mono<MovementResponse> saveMovement(MovementRequest request) {
        return movementServiceAdapter.saveMovement(request)
                .map(entity -> {
                    log.info("|-> Movement created. Type: {} with amount {}", request.getMovementType(), request.getAmount());
                    MovementResponse response = new MovementResponse();
                    response.setMovementId(entity.getMovementId());
                    return response;
                });
    }
}
