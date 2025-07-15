package com.test.sp.customer.infrastructure.output.repository;

import com.test.sp.customer.infrastructure.output.repository.entity.CustomerEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<CustomerEntity, UUID> {

    @Query("SELECT COUNT(P.IDENTIFICATION) > 0 FROM PERSON P " +
            "WHERE P.IDENTIFICATION = :identification")
    Mono<Boolean> findByIdentification(String identification);

    Mono<CustomerEntity> findByPersonId(UUID personId);
}
