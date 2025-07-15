package com.test.sp.customer.application.service;

import com.test.sp.customer.application.output.port.CustomerServiceAdapter;
import com.test.sp.customer.application.output.port.PersonServiceAdapter;
import com.test.sp.customer.domain.CustomerRequest;
import com.test.sp.customer.domain.CustomerResponse;
import com.test.sp.customer.domain.GetCustomers;
import com.test.sp.customer.infrastructure.exception.PostCustomerException;
import com.test.sp.customer.infrastructure.output.repository.mapper.CustomerMapper;
import com.test.sp.customer.application.input.port.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerServiceAdapter customerServiceAdapter;
    private final PersonServiceAdapter personServiceAdapter;
    private final CustomerMapper customerMapper;

    @Override
    public Mono<CustomerResponse> postCustomer(@NotNull @Valid CustomerRequest request) {
        log.info("|-> Starts process of creating new customer service");
        return customerServiceAdapter.verifyCustomer(request.getIdentification())
                .flatMap(existsCustomer -> {
                    if (Boolean.TRUE.equals(existsCustomer)) {
                        log.error("|-> Error. Customer exists in DB");
                        return Mono.error(new PostCustomerException());
                    } else {
                        log.info("|-> Customer validated successfully");
                        return personServiceAdapter.savePerson(request)
                                .flatMap(personEntity ->
                                        customerServiceAdapter.saveCustomer(request, personEntity))
                                .map(customerEntity -> {
                                    log.info("|-> Customer created with id: {}", customerEntity.getCustomerId());
                                    CustomerResponse response = new CustomerResponse();
                                    response.setCustomerId(customerEntity.getCustomerId());
                                    return response;
                                });
                    }
                }).doOnError(throwable -> log.error(
                        "|-> Error create new customer. Error detail {}", throwable.getMessage()
                ));
    }

    @Override
    public Mono<Void> putCustomer(@NotNull @NotBlank UUID customerId,
                                  @NotNull @Valid CustomerRequest request) {
        log.info("|-> Starts process of updating customer by ID {}", customerId);
        return customerServiceAdapter.findByCustomerId(customerId)
                .flatMap(customerEntity -> {
                    log.info("|-> Customer id exists in the system.");
                    return customerServiceAdapter.updateCustomer(request, customerEntity)
                            .flatMap(entity ->
                                    personServiceAdapter.updatePerson(request, entity));
                }).doOnError(throwable -> log.error(
                        "|-> Error update customer. Error detail {}", throwable.getMessage()
                ))
                .then();
    }

    @Override
    public Flux<GetCustomers> getCustomers() {
        log.info("|-> Starts process of search customers");
        return personServiceAdapter.findAllPersons()
                .flatMap(person ->
                        customerServiceAdapter.findCustomerByPersonId(person.getPersonId())
                                .map(customer ->
                                        customerMapper.getCustomerAll(person, customer)))
                .doOnError(throwable -> log.error(
                        "|-> Error search all customers. Error detail {}", throwable.getMessage()
                ));
    }

    @Override
    public Mono<Void> deleteCustomer(@NotNull @NotBlank UUID customerId) {
        log.info("|-> Starts process of deleting customer by ID {}", customerId);
        return customerServiceAdapter.findByCustomerId(customerId)
                .flatMap(customer -> {
                    log.info("|-> Customer exists in the system.");
                    return customerServiceAdapter.deleteByCustomerId(customerId)
                            .then(personServiceAdapter.deleteByPersonId(customer.getPersonId()));
                })
                .doOnError(throwable -> log.error(
                        "|-> Error delete customer. Error detail {}", throwable.getMessage()))
                .doOnSuccess(v -> log.info("|-> Customer was deleted."))
                .then();
    }

    @Override
    public Mono<GetCustomers> getCustomerById(@NotNull @NotBlank UUID customerId) {
        log.info("|-> Starts process of searching customer by ID {}", customerId);
        return customerServiceAdapter.findByCustomerId(customerId)
                .flatMap(customer -> {
                    log.info("|-> Customer ID exists in the system.");
                    return personServiceAdapter.findByPersonId(customer.getPersonId())
                            .map(person -> customerMapper.getCustomerAll(person, customer));
                })
                .doOnError(throwable -> log.error(
                        "|-> Error search customer by id. Error detail {}", throwable.getMessage()));

    }

}
