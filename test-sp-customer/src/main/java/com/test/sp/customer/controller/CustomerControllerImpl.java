package com.test.sp.customer.controller;

import com.test.sp.customer.api.CustomersApi;
import com.test.sp.customer.model.GetCustomersResponse;
import com.test.sp.customer.model.PostCustomerRequest;
import com.test.sp.customer.model.PostCustomerResponse;
import com.test.sp.customer.model.PutCustomerByIdRequest;
import com.test.sp.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerControllerImpl implements CustomersApi {

    private final CustomerService customerService;

    @Override
    public Mono<ResponseEntity<PostCustomerResponse>> postCustomer(Mono<PostCustomerRequest> postCustomerRequest,
                                                                   ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the customer creation method.");
        return postCustomerRequest
                .flatMap(customerService::postCustomer)
                .map(postCustomerResponse ->
                        new ResponseEntity<>(postCustomerResponse, HttpStatus.CREATED));
    }

    @Override
    public Mono<ResponseEntity<Void>> putCustomerById(Integer customerId,
                                                      Mono<PutCustomerByIdRequest> putCustomerByIdRequest,
                                                      ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the customer update method.");
        return putCustomerByIdRequest
                .flatMap(request ->
                        customerService.putCustomer(customerId, request))
                .map(s -> new ResponseEntity<>(HttpStatus.OK));
    }

    @Override
    public Mono<ResponseEntity<Flux<GetCustomersResponse>>> getCustomers(ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the query method for all customers.");
        return customerService.getCustomers()
                .map(getCustomersResponseFlux ->
                        new ResponseEntity<>(getCustomersResponseFlux, HttpStatus.OK));

    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(Integer customerId,
                                                     ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the customer delete method.");
        return customerService.deleteCustomer(customerId)
                .map(s -> new ResponseEntity<>(HttpStatus.OK));
    }

    @Override
    public Mono<ResponseEntity<GetCustomersResponse>> getCustomerById(Integer customerId,
                                                                      ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the customer search method.");
        return customerService.getCustomerById(customerId)
                .map(getCustomersResponse -> new ResponseEntity<>(getCustomersResponse, HttpStatus.OK));
    }
}
