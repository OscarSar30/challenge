package com.test.sp.customer.infrastructure.output.repository;

import com.test.sp.customer.infrastructure.output.repository.entity.PersonEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<PersonEntity, UUID> {
}
