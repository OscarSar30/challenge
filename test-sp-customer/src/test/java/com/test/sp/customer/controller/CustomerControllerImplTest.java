package com.test.sp.customer.controller;

import com.test.sp.customer.model.GetCustomersResponse;
import com.test.sp.customer.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static com.test.sp.customer.util.MockData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerImplTest{

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerControllerImpl customerController;

    @Test
    void testCreateCustomer() {
        when(customerService.postCustomer(mockPostCustomerRequest()))
                .thenReturn(Mono.just(mockPostCustomerResponse()));

        StepVerifier.create(customerController.postCustomer(
                        Mono.just(mockPostCustomerRequest()), null))
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.CREATED &&
                        Objects.equals(response.getBody(), mockPostCustomerResponse()))
                .verifyComplete();
    }

    @Test
    void testDeleteCustomer() {
        when(customerService.deleteCustomer(MOCK_CUSTOMER_ID))
                .thenReturn(Mono.empty());

        StepVerifier.create(customerController.deleteCustomer(
                        MOCK_CUSTOMER_ID, any()))
                .expectComplete()
                .verify();

    }

    @Test
    void testUpdateCustomer() {
        when(customerService.putCustomer(MOCK_CUSTOMER_ID, mockPutCustomerByIdRequest()))
                .thenReturn(Mono.empty());

        StepVerifier.create(customerController.putCustomerById(MOCK_CUSTOMER_ID,
                        Mono.just(mockPutCustomerByIdRequest()), null))
                .expectComplete()
                .verify();
    }

    @Test
    void testGetCustomers() {
        Flux<GetCustomersResponse> responseFlux = Flux.just(mockGetCustomersResponse());
        when(customerService.getCustomers())
                .thenReturn(Mono.just(responseFlux));

        StepVerifier.create(customerController.getCustomers(null))
                .expectNextMatches(response ->
                        response != null && Objects.equals(response.getBody(), mockGetCustomersResponse()))
                .expectComplete();
    }

    @Test
    void testGetCustomerById() {
        when(customerService.getCustomerById(MOCK_CUSTOMER_ID))
                .thenReturn(Mono.just(mockGetCustomersResponse()));

        StepVerifier.create(customerController.getCustomerById(
                        MOCK_CUSTOMER_ID, null))
                .expectNextMatches(response ->
                        response != null && Objects.equals(response.getBody(), mockGetCustomersResponse()))
                .verifyComplete();
    }

}