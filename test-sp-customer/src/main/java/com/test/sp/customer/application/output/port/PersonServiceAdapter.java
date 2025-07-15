package com.test.sp.customer.application.output.port;

import com.test.sp.customer.domain.CustomerRequest;
import com.test.sp.customer.infrastructure.output.repository.entity.CustomerEntity;
import com.test.sp.customer.infrastructure.output.repository.entity.PersonEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PersonServiceAdapter {

    Mono<PersonEntity> savePerson(@NotNull @Valid CustomerRequest request);

    Mono<PersonEntity> updatePerson(@NotNull @Valid CustomerRequest request,
                                    CustomerEntity customerEntity);

    Flux<PersonEntity> findAllPersons();

    Mono<PersonEntity> findByPersonId(UUID personId);

    Mono<Void> deleteByPersonId(UUID personId);
}
