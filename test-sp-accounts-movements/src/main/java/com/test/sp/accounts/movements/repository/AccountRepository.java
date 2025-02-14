package com.test.sp.accounts.movements.repository;

import com.test.sp.accounts.movements.domain.Account;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, Integer> {
    Mono<Account> findByAccountNumber(String accountNumber);
}
