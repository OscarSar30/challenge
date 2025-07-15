package com.test.sp.customer.infrastructure.output.adapter;

import com.test.sp.customer.application.output.port.CustomerServiceAdapter;
import com.test.sp.customer.domain.CustomerRequest;
import com.test.sp.customer.infrastructure.exception.CustomerExceptionNotFound;
import com.test.sp.customer.infrastructure.output.repository.CustomerRepository;
import com.test.sp.customer.infrastructure.output.repository.entity.CustomerEntity;
import com.test.sp.customer.infrastructure.output.repository.entity.PersonEntity;
import com.test.sp.customer.infrastructure.output.repository.mapper.CustomerMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceAdapterImpl implements CustomerServiceAdapter {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Mono<Boolean> verifyCustomer(String identification) {
        log.info("|-> Initiate verify customer adapter.");
        return customerRepository.findByIdentification(identification)
                .doOnSuccess(aBoolean -> log.info(
                        "|-> Adapter:: Search data customer successfully."))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error customer project. Error detail {}", error.getMessage()));
    }

    @Override
    public Mono<CustomerEntity> saveCustomer(CustomerRequest request,
                                             PersonEntity personEntity) {
        log.info("|-> Initiate save customer adapter.");
        return customerRepository.save(customerMapper.requestToCustomerEntity(request, personEntity))
                .doOnSuccess(customersEntity -> log.info(
                        "|-> Adapter:: Creation customer successfully."))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error create customer. Error detail {}", error.getMessage()));
    }

    @Override
    public Mono<CustomerEntity> findByCustomerId(UUID customerId) {
        log.info("|-> Initiate search customer by id adapter.");
        return customerRepository.findById(customerId)
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Customer not found with ID {}", customerId);
                    return Mono.error(new CustomerExceptionNotFound());
                }))
                .doOnSuccess(customersEntity -> log.info(
                        "|-> Adapter:: Search data customer by id successfully."))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error search customer. Error detail {}", error.getMessage()));
    }

    @Override
    public Mono<CustomerEntity> updateCustomer(@NotNull @Valid CustomerRequest request,
                                               CustomerEntity customerEntity) {
        log.info("|-> Initiate update customer adapter.");
        return customerRepository.save(customerMapper.requestToCustomerEntity(request, customerEntity))
                .doOnSuccess(entity -> log.info(
                        "|-> Adapter:: Update customer successfully."))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error update customer by ID {}. Error detail {}", customerEntity.getCustomerId(),
                                error.getMessage()));
    }

    @Override
    public Mono<CustomerEntity> findCustomerByPersonId(UUID personId) {
        log.info("|-> Initiate search customer by person id adapter.");
        return customerRepository.findByPersonId(personId)
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Customer not found by person id {}: ", personId);
                    return Mono.error(new CustomerExceptionNotFound());
                }))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error search customer by person id. Error detail {}", error.getMessage()));
    }

    @Override
    public Mono<Void> deleteByCustomerId(UUID customerId) {
        log.info("|-> Initiate delete customer by id adapter.");
        return customerRepository.deleteById(customerId)
                .doOnSuccess(unused -> log.info(
                        "Adapter:: Delete data customer successfully"))
                .doOnError(error ->
                        log.error("|-> Adapter:: Error delete customer. Error detail {}", error.getMessage()));
    }
}
