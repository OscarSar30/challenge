package com.test.sp.accounts.movements.infrastructure.output.repository;

import com.test.sp.accounts.movements.infrastructure.output.repository.entity.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<AccountEntity, UUID> {
    Mono<AccountEntity> findByAccountNumber(String accountNumber);
    Mono<AccountEntity> findByAccountId(Integer accountId);
}
