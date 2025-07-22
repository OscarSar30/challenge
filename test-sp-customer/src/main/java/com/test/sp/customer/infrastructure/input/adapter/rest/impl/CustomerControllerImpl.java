package com.test.sp.customer.infrastructure.input.adapter.rest.impl;

import com.test.sp.customer.api.CustomersApi;
import com.test.sp.customer.infrastructure.input.adapter.rest.mapper.CustomerApiMapper;
import com.test.sp.customer.model.GetCustomersResponse;
import com.test.sp.customer.model.PostCustomerRequest;
import com.test.sp.customer.model.PostCustomerResponse;
import com.test.sp.customer.model.PutCustomerByIdRequest;
import com.test.sp.customer.application.input.port.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerControllerImpl implements CustomersApi {

    private final CustomerService customerService;
    private final CustomerApiMapper apiMapper;

    @Override
    public Mono<ResponseEntity<PostCustomerResponse>> postCustomer(Mono<PostCustomerRequest> postCustomerRequest,
                                                                   ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the customer creation method.");
        return postCustomerRequest
                .map(apiMapper::toCustomerRequest)
                .flatMap(customerService::postCustomer)
                .doOnError(e -> log.error("<-| Error while creating customer. Error: {}", e.getMessage()))
                .map(apiMapper::toCustomerResponse)
                .map(postCustomerResponse ->
                        new ResponseEntity<>(postCustomerResponse, HttpStatus.CREATED));
    }

    @Override
    public Mono<ResponseEntity<Void>> putCustomerById(UUID customerId,
                                                      Mono<PutCustomerByIdRequest> putCustomerByIdRequest,
                                                      ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the customer update method.");
        return putCustomerByIdRequest
                .map(apiMapper::toCustomerRequest)
                .flatMap(request ->
                        customerService.putCustomer(customerId, request))
                .doOnError(e -> log.error("<-| Error while updating customer. Error: {}", e.getMessage()))
                .thenReturn(ResponseEntity.ok().build());
    }

    @Override
    public Mono<ResponseEntity<Flux<GetCustomersResponse>>> getCustomers(ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the query method for all customers.");
        Flux<GetCustomersResponse> response =
                customerService.getCustomers()
                        .doOnError(e -> log.error("<-| Error while searching customers. Error: {}", e.getMessage()))
                        .map(apiMapper::toGetCustomersResponse);
        return Mono.just(ResponseEntity.ok().body(response));

    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(UUID customerId,
                                                     ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the customer delete method.");
        return customerService.deleteCustomer(customerId)
                .doOnError(e -> log.error("<-| Error while deleting customer. Error: {}", e.getMessage()))
                .thenReturn(ResponseEntity.ok().build());
    }

    @Override
    public Mono<ResponseEntity<GetCustomersResponse>> getCustomerById(UUID customerId,
                                                                      ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the customer search method.");
        return customerService.getCustomerById(customerId)
                .doOnError(e -> log.error("<-| Error while searching customer by id. Error: {}", e.getMessage()))
                .map(apiMapper::toGetCustomersResponse)
                .map(getCustomersResponse ->
                        new ResponseEntity<>(getCustomersResponse, HttpStatus.OK));
    }
}