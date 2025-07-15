package com.test.sp.customer.infrastructure.output.adapter;

import com.test.sp.customer.infrastructure.exception.CustomerExceptionNotFound;
import com.test.sp.customer.infrastructure.output.repository.CustomerRepository;
import com.test.sp.customer.infrastructure.output.repository.mapper.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static com.test.sp.customer.util.MockData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceAdapterImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceAdapterImpl customerServiceAdapter;

    @Test
    void verifyCustomerWhenIdentificationIsOK() {
        when(customerRepository.findByIdentification(any()))
                .thenReturn(Mono.just(true));

        StepVerifier.create(customerServiceAdapter.verifyCustomer(MOCK_IDENTIFICATION))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void verifyCustomerReturnError() {
        when(customerRepository.findByIdentification(any()))
                .thenReturn(Mono.error(new RuntimeException()));

        StepVerifier.create(customerServiceAdapter.verifyCustomer(MOCK_IDENTIFICATION))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void saveCustomerWhenIsOK() {
        var request = mockCustomerRequest();
        var personEntity = mockPersonEntity();
        var customerEntity = mockCustomerEntity();

        when(customerRepository.save(any()))
                .thenReturn(Mono.just(customerEntity));

        StepVerifier.create(customerServiceAdapter.saveCustomer(request, personEntity))
                .expectNext(customerEntity)
                .verifyComplete();
    }

    @Test
    void saveCustomerReturnError() {
        var request = mockCustomerRequest();
        var personEntity = mockPersonEntity();

        when(customerRepository.save(any()))
                .thenReturn(Mono.error(new RuntimeException()));

        StepVerifier.create(customerServiceAdapter.saveCustomer(request, personEntity))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void findByCustomerIdWhenIsOK() {
        var customerEntity = mockCustomerEntity();

        when(customerRepository.findById(any(UUID.class)))
                .thenReturn(Mono.just(customerEntity));

        StepVerifier.create(customerServiceAdapter.findByCustomerId(UUID.randomUUID()))
                .expectNext(customerEntity)
                .verifyComplete();
    }

    @Test
    void findByCustomerIdReturnNotFound() {
        when(customerRepository.findById(any(UUID.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(customerServiceAdapter.findByCustomerId(UUID.randomUUID()))
                .expectError(CustomerExceptionNotFound.class)
                .verify();
    }

    @Test
    void updateCustomerWhenIsOK() {
        var customerEntity = mockCustomerEntity();
        var request = mockCustomerRequest();

        when(customerRepository.save(any()))
                .thenReturn(Mono.just(customerEntity));

        StepVerifier.create(customerServiceAdapter.updateCustomer(request, customerEntity))
                .expectNext(customerEntity)
                .verifyComplete();
    }

    @Test
    void updateCustomerReturnError() {
        var customerEntity = mockCustomerEntity();
        var request = mockCustomerRequest();

        when(customerRepository.save(any()))
                .thenReturn(Mono.error(new RuntimeException()));

        StepVerifier.create(customerServiceAdapter.updateCustomer(request, customerEntity))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void findByPersonIdWhenIsOK() {
        var customerEntity = mockCustomerEntity();

        when(customerRepository.findByPersonId(any()))
                .thenReturn(Mono.just(customerEntity));

        StepVerifier.create(customerServiceAdapter.findByPersonId(UUID.randomUUID()))
                .expectNext(customerEntity)
                .verifyComplete();
    }

    @Test
    void findByPersonIdReturnNotFound() {
        when(customerRepository.findByPersonId(any()))
                .thenReturn(Mono.empty());

        StepVerifier.create(customerServiceAdapter.findByPersonId(UUID.randomUUID()))
                .expectError(CustomerExceptionNotFound.class)
                .verify();
    }

    @Test
    void deleteByCustomerIdWhenIsOK() {
        when(customerRepository.deleteById(any(UUID.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(customerServiceAdapter.deleteByCustomerId(UUID.randomUUID()))
                .expectNext()
                .verifyComplete();
    }

    @Test
    void deleteByCustomerIdReturnError() {
        when(customerRepository.deleteById(any(UUID.class)))
                .thenReturn(Mono.error(new RuntimeException()));

        StepVerifier.create(customerServiceAdapter.deleteByCustomerId(UUID.randomUUID()))
                .expectError(RuntimeException.class)
                .verify();
    }

}
