package com.test.sp.customer.repository;

import com.test.sp.customer.repository.entity.CustomerEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<CustomerEntity, Integer> {

    @Query("SELECT COUNT(P.IDENTIFICATION) > 0 FROM PERSON P " +
            "WHERE P.IDENTIFICATION = :identification")
    Mono<Boolean> findByIdentification(String identification);

    Mono<CustomerEntity> findByPersonId(Integer personId);
}
