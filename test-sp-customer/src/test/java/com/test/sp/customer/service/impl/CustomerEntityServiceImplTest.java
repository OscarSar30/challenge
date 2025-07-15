package com.test.sp.customer.service.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerEntityServiceImplTest {

    /*@Mock
    private PersonRepository personRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;
    @InjectMocks
    CustomerServiceImpl customerService;

    @Test
    void testCreateCustomerSuccess() {
        when(customerRepository.findByIdentification(mockPostCustomerRequest().getIdentification()))
                .thenReturn(Mono.just(false));
        when(personRepository.save(any()))
                .thenReturn(Mono.just(mockPersonEntity()));
        when(customerRepository.save(any()))
                .thenReturn(Mono.just(mockCustomerEntity()));
        StepVerifier.create(customerService.postCustomer(mockPostCustomerRequest()))
                .expectNextMatches(responseBody ->
                        responseBody.getCustomerId().equals(MOCK_CUSTOMER_ID))
                .verifyComplete();
    }

    @Test
    void testPostCustomerReturnError() {
        when(customerRepository.findByIdentification(mockPostCustomerRequest().getIdentification()))
                .thenReturn(Mono.just(true));
        StepVerifier.create(customerService.postCustomer(mockPostCustomerRequest()))
                .expectError(PostCustomerException.class)
                .verify();
    }

    @Test
    void testPutCustomerSuccess() {
        when(customerRepository.findById(MOCK_CUSTOMER_ID))
                .thenReturn(Mono.just(mockCustomerEntity()));
        when(customerRepository.save(any()))
                .thenReturn(Mono.just(mockCustomerEntity()));
        when(personRepository.save(any()))
                .thenReturn(Mono.just(mockPersonEntity()));
        StepVerifier.create(customerService.putCustomer(MOCK_CUSTOMER_ID, mockPutCustomerByIdRequest()))
                .verifyComplete();
    }

    @Test
    void testPutCustomerReturnCustomerNotFoundException() {
        when(customerRepository.findById(MOCK_CUSTOMER_ID))
                .thenReturn(Mono.empty());

        StepVerifier.create(customerService.putCustomer(MOCK_CUSTOMER_ID, mockPutCustomerByIdRequest()))
                .expectError(CustomerExceptionNotFound.class)
                .verify();
    }*/

}
