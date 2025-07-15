package com.test.sp.customer.application.service;

import com.test.sp.customer.application.output.port.CustomerServiceAdapter;
import com.test.sp.customer.application.output.port.PersonServiceAdapter;
import com.test.sp.customer.infrastructure.exception.CustomerExceptionNotFound;
import com.test.sp.customer.infrastructure.exception.PersonExceptionNotFound;
import com.test.sp.customer.infrastructure.exception.PostCustomerException;
import com.test.sp.customer.infrastructure.output.repository.mapper.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static com.test.sp.customer.util.MockData.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerServiceAdapter customerServiceAdapter;

    @Mock
    private PersonServiceAdapter personServiceAdapter;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    CustomerServiceImpl customerService;

    @Test
    void testCreateCustomerSuccess() {
        var request = mockCustomerRequest();
        var personEntity = mockPersonEntity();
        var customerEntity = mockCustomerEntity();

        when(customerServiceAdapter.verifyCustomer(any()))
                .thenReturn(Mono.just(false));
        when(personServiceAdapter.savePerson(any()))
                .thenReturn(Mono.just(personEntity));
        when(customerServiceAdapter.saveCustomer(any(), any()))
                .thenReturn(Mono.just(customerEntity));
        StepVerifier.create(customerService.postCustomer(request))
                .expectNextMatches(responseEntity -> {
                    assertNotNull(responseEntity.getCustomerId());
                    return responseEntity.getCustomerId().equals(responseEntity.getCustomerId());
                })
                .verifyComplete();
    }

    @Test
    void testPostCustomerReturnError() {
        var request = mockCustomerRequest();

        when(customerServiceAdapter.verifyCustomer(any()))
                .thenReturn(Mono.just(true));
        StepVerifier.create(customerService.postCustomer(request))
                .expectError(PostCustomerException.class)
                .verify();
    }

    @Test
    void testPutCustomerSuccess() {
        var id = UUID.randomUUID();
        var request = mockCustomerRequest();
        var personEntity = mockPersonEntity();
        var customerEntity = mockCustomerEntity();

        when(customerServiceAdapter.findByCustomerId(any()))
                .thenReturn(Mono.just(customerEntity));
        when(customerServiceAdapter.updateCustomer(any(), any()))
                .thenReturn(Mono.just(customerEntity));
        when(personServiceAdapter.updatePerson(any(), any()))
                .thenReturn(Mono.just(personEntity));
        StepVerifier.create(customerService.putCustomer(id, request))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void testPutCustomerReturnError() {
        var id = UUID.randomUUID();
        var request = mockCustomerRequest();

        when(customerServiceAdapter.findByCustomerId(any()))
                .thenReturn(Mono.error(new CustomerExceptionNotFound()));

        StepVerifier.create(customerService.putCustomer(id, request))
                .expectError(CustomerExceptionNotFound.class)
                .verify();
    }

    @Test
    void getCustomers() {
        var response = mockGetCustomers();
        var personEntity = mockPersonEntity();
        var customerEntity = mockCustomerEntity();

        when(personServiceAdapter.findAllPersons())
                .thenReturn(Flux.just(personEntity));
        when(customerServiceAdapter.findByPersonId(any()))
                .thenReturn(Mono.just(customerEntity));
        when(customerMapper.getCustomerAll(any(), any()))
                .thenReturn(response);

        StepVerifier.create(customerService.getCustomers())
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    void getCustomersReturnError() {
        when(personServiceAdapter.findAllPersons())
                .thenReturn(Flux.error(new PersonExceptionNotFound()));

        StepVerifier.create(customerService.getCustomers())
                .expectError(PersonExceptionNotFound.class)
                .verify();
    }

    @Test
    void deleteCustomerById() {
        var id = UUID.randomUUID();
        var customerEntity = mockCustomerEntity();

        when(customerServiceAdapter.findByCustomerId(any()))
                .thenReturn(Mono.just(customerEntity));
        when(customerServiceAdapter.deleteByCustomerId(any()))
                .thenReturn(Mono.empty());
        when(personServiceAdapter.deleteByPersonId(any()))
                .thenReturn(Mono.empty());

        StepVerifier.create(customerService.deleteCustomer(id))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void deleteCustomerByIdReturnError() {
        var id = UUID.randomUUID();

        when(customerServiceAdapter.findByCustomerId(any()))
                .thenReturn(Mono.error(new CustomerExceptionNotFound()));

        StepVerifier.create(customerService.deleteCustomer(id))
                .expectError(CustomerExceptionNotFound.class)
                .verify();
    }

    @Test
    void getCustomerById() {
        var id = UUID.randomUUID();
        var personEntity = mockPersonEntity();
        var customerEntity = mockCustomerEntity();
        var response = mockGetCustomers();

        when(customerServiceAdapter.findByCustomerId(any()))
                .thenReturn(Mono.just(customerEntity));
        when(personServiceAdapter.findByPersonId(any()))
                .thenReturn(Mono.just(personEntity));
        when(customerMapper.getCustomerAll(any(), any()))
                .thenReturn(response);

        StepVerifier.create(customerService.getCustomerById(id))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void getCustomerByIdReturnError() {
        var id = UUID.randomUUID();

        when(customerServiceAdapter.findByCustomerId(any()))
                .thenReturn(Mono.error(new CustomerExceptionNotFound()));

        StepVerifier.create(customerService.getCustomerById(id))
                .expectError(CustomerExceptionNotFound.class)
                .verify();
    }

}
