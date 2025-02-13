package com.test.sp.customer.repository;

import com.test.sp.customer.domain.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<Person, Integer> {
    Mono<Person> findByIdentification(String identification);
}
