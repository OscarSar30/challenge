package com.test.sp.customer.infrastructure.output.adapter;

import com.test.sp.customer.application.output.port.PersonServiceAdapter;
import com.test.sp.customer.domain.CustomerRequest;
import com.test.sp.customer.infrastructure.exception.PersonExceptionNotFound;
import com.test.sp.customer.infrastructure.output.repository.PersonRepository;
import com.test.sp.customer.infrastructure.output.repository.entity.CustomerEntity;
import com.test.sp.customer.infrastructure.output.repository.entity.PersonEntity;
import com.test.sp.customer.infrastructure.output.repository.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceAdapterImpl implements PersonServiceAdapter {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    public Mono<PersonEntity> savePerson(CustomerRequest request) {
        log.info("|-> Initiate save person adapter.");
        return personRepository.save(personMapper.requestToEntity(request))
                .doOnSuccess(customersEntity -> log.info(
                        "|-> Adapter:: Creation customer successfully."))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error create customer. Error detail {}", error.getMessage()));
    }

    @Override
    public Mono<PersonEntity> updatePerson(CustomerRequest request,
                                           CustomerEntity customerEntity) {
        log.info("|-> Initiate update person adapter.");
        return personRepository.save(personMapper.requestToEntity(request, customerEntity))
                .doOnSuccess(personEntity -> log.info(
                        "|-> Adapter:: Update person successfully."))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error update person by ID {}. Error detail {}", customerEntity.getCustomerId(),
                                error.getMessage()));
    }

    @Override
    public Flux<PersonEntity> findAllPersons() {
        log.info("|-> Initiate search all persons adapter.");
        return personRepository.findAll()
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Persons not found");
                    return Mono.error(new PersonExceptionNotFound());
                }))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error search person. Error detail {}", error.getMessage()));
    }

    @Override
    public Mono<PersonEntity> findByPersonId(UUID personId) {
        log.info("|-> Initiate search person by id adapter.");
        return personRepository.findById(personId)
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Person not found with ID {}", personId);
                    return Mono.error(new PersonExceptionNotFound());
                }))
                .doOnSuccess(personEntity -> log.info(
                        "|-> Adapter:: Search data person by id successfully."))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error search person id. Error detail {}", error.getMessage()));
    }

    @Override
    public Mono<Void> deleteByPersonId(UUID personId) {
        log.info("|-> Initiate delete person by id adapter.");
        return personRepository.deleteById(personId)
                .doOnSuccess(unused -> log.info(
                        "Adapter:: Delete data person successfully"))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error delete person. Error detail {}", error.getMessage()));
    }
}
