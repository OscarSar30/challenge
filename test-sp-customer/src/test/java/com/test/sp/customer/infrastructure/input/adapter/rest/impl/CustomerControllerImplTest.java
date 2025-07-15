package com.test.sp.customer.infrastructure.input.adapter.rest.impl;

import com.test.sp.customer.application.input.port.CustomerService;
import com.test.sp.customer.domain.CustomerRequest;
import com.test.sp.customer.domain.CustomerResponse;
import com.test.sp.customer.domain.GetCustomers;
import com.test.sp.customer.infrastructure.input.adapter.rest.mapper.CustomerApiMapper;
import com.test.sp.customer.model.PostCustomerRequest;
import com.test.sp.customer.model.PutCustomerByIdRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static com.test.sp.customer.util.MockData.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomerControllerImplTest.class)
class CustomerControllerImplTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerApiMapper apiMapper;

    @InjectMocks
    private CustomerControllerImpl customerController;

    @Test
    void testCreateCustomer() {
        var request = mockPostCustomerRequest();
        var response = mockPostCustomerResponse();

        when(apiMapper.toCustomerRequest(any(PostCustomerRequest.class)))
                .thenReturn(new CustomerRequest());
        when(customerService.postCustomer(any()))
                .thenReturn(Mono.just(new CustomerResponse()));
        when(apiMapper.toCustomerResponse(any()))
                .thenReturn(response);

        customerController.postCustomer(Mono.just(request), null)
                .as(StepVerifier::create)
                .expectNextMatches(responseEntity -> {
                    assertNotNull(responseEntity.getBody());
                    return responseEntity.getBody().equals(response);
                })
                .verifyComplete();
    }

    @Test
    void testUpdateCustomer() {
        var request = mockPutCustomerByIdRequest();

        when(apiMapper.toCustomerRequest(any(PutCustomerByIdRequest.class)))
                .thenReturn(new CustomerRequest());
        when(customerService.putCustomer(any(), any()))
                .thenReturn(Mono.empty());

        customerController.putCustomerById(UUID.randomUUID(), Mono.just(request), null)
                .as(StepVerifier::create)
                .expectNextMatches(responseEntity -> {
                    assertNotNull(responseEntity);
                    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
                    assertNull(responseEntity.getBody());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void testGetCustomers() {
        var response = mockGetCustomersResponse();

        when(customerService.getCustomers())
                .thenReturn(Flux.just(new GetCustomers()));
        when(apiMapper.toGetCustomersResponse(any()))
                .thenReturn(response);

        customerController.getCustomers(null)
                .as(StepVerifier::create)
                .assertNext(responseEntity -> {
                    assertNotNull(responseEntity);
                    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
                    var bodyFlux = responseEntity.getBody();
                    assertNotNull(bodyFlux);
                    StepVerifier.create(bodyFlux)
                            .expectNext(response)
                            .verifyComplete();
                })
                .verifyComplete();
    }

    @Test
    void testGetCustomerById() {
        var response = mockGetCustomersResponse();

        when(customerService.getCustomerById(any()))
                .thenReturn(Mono.just(new GetCustomers()));
        when(apiMapper.toGetCustomersResponse(any()))
                .thenReturn(response);

        customerController.getCustomerById(UUID.randomUUID(),null)
                .as(StepVerifier::create)
                .expectNextMatches(responseEntity -> {
                    assertNotNull(responseEntity.getBody());
                    return responseEntity.getBody().equals(response);
                })
                .verifyComplete();
    }

    @Test
    void testDeleteCustomerById() {
        when(customerService.deleteCustomer(any()))
                .thenReturn(Mono.empty());

        customerController.deleteCustomer(UUID.randomUUID(), null)
                .as(StepVerifier::create)
                .expectNextMatches(responseEntity -> {
                    assertNotNull(responseEntity);
                    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
                    assertNull(responseEntity.getBody());
                    return true;
                })
                .verifyComplete();
    }

}