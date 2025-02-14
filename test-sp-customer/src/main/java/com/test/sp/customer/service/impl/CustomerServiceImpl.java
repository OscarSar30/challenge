package com.test.sp.customer.service.impl;

import com.test.sp.customer.exception.PostCustomerException;
import com.test.sp.customer.exception.CustomerExceptionNotFound;
import com.test.sp.customer.model.GetCustomersResponse;
import com.test.sp.customer.model.PostCustomerRequest;
import com.test.sp.customer.model.PostCustomerResponse;
import com.test.sp.customer.model.PutCustomerByIdRequest;
import com.test.sp.customer.repository.CustomerRepository;
import com.test.sp.customer.repository.PersonRepository;
import com.test.sp.customer.service.CustomerService;
import com.test.sp.customer.service.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final PersonRepository personRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Mono<PostCustomerResponse> postCustomer(PostCustomerRequest request) {
        log.info("|-> Starts process of creating new customer service");
        return customerRepository.findByIdentification(request.getIdentification())
                .flatMap(existsCustomer -> {
                    if (Boolean.TRUE.equals(existsCustomer)) {
                        log.error("|-> Error. Customer exists in DB");
                        return Mono.error(new PostCustomerException());
                    } else {
                        log.info("|-> Customer validated successfully");
                        return personRepository.save(customerMapper.postRequestClientToPersonEntity(request))
                                .flatMap(response ->
                                        customerRepository.save(customerMapper.requestToCustomerEntity(request, response)))
                                .doOnError(e -> log.error("Error create customer: {}", e.getMessage()))
                                .map(customer -> {
                                    log.info("|-> Customer created with id: {}", customer.getCustomerId());
                                    PostCustomerResponse postCustomerResponse = new PostCustomerResponse();
                                    postCustomerResponse.setCustomerId(customer.getCustomerId());
                                    return postCustomerResponse;
                                });
                    }
                });
    }

    @Override
    public Mono<Void> putCustomer(Integer customerId,
                                  PutCustomerByIdRequest request) {
        log.info("|-> Starts process of updating customer by ID {}", customerId);
        return customerRepository.findById(customerId)
                .flatMap(customer -> {
                    log.info("|-> Customer exists in the system.");
                    return customerRepository.save(customerMapper.putRequestClientToCustomerEntity(request, customer))
                            .flatMap(entity ->
                                    personRepository.save(customerMapper.putRequestClientToPersonEntity(request, entity)));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Customer not found with ID {}",customerId);
                    return Mono.error(new CustomerExceptionNotFound());
                }))
                .then();
    }

    @Override
    public Mono<Flux<GetCustomersResponse>> getCustomers() {
        log.info("|-> Starts process of search customers");
        return personRepository.findAll()
                .flatMap(person ->
                        customerRepository.findByPersonId(person.personId)
                                .map(customer -> customerMapper.getCustomerAll(person, customer)))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Customers not found");
                    return Mono.error(new CustomerExceptionNotFound());
                }))
                .collectList()
                .map(Flux::fromIterable);
    }

    @Override
    public Mono<Void> deleteCustomer(Integer customerId) {
        log.info("|-> Starts process of deleting customer by ID {}", customerId);
        return customerRepository.findById(customerId)
                .flatMap(customer -> {
                    log.info("|-> Customer exists in the system.");
                    return customerRepository.deleteById(customerId)
                            .then(personRepository.deleteById(customer.getPersonId()));
                })
                .doOnSuccess(v -> log.info("|-> Customer was deleted."))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Customer not found with ID {}", customerId);
                    return Mono.error(new CustomerExceptionNotFound());
                }))
                .then();
    }

    @Override
    public Mono<GetCustomersResponse> getCustomerById(Integer customerId) {
        log.info("|-> Starts process of searching customer by ID {}", customerId);
        return customerRepository.findById(customerId)
                .flatMap(customer -> {
                    log.info("|-> Customer exists in the system.");
                    return personRepository.findById(customer.getPersonId())
                            .map(person -> customerMapper.getCustomerAll(person, customer));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("|-> Customer not found with ID {}",customerId);
                    return Mono.error(new CustomerExceptionNotFound());
                }));
    }

}
