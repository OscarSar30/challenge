package com.test.sp.accounts.movements.repository;

import com.test.sp.accounts.movements.repository.entity.AccountEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<AccountEntity, Integer> {
    Mono<AccountEntity> findByAccountNumber(String accountNumber);
    Mono<AccountEntity> findByAccountId(Integer accountId);
}
