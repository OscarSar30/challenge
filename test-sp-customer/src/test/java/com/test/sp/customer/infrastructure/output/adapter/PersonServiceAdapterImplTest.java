package com.test.sp.customer.infrastructure.output.adapter;

import com.test.sp.customer.infrastructure.exception.PersonExceptionNotFound;
import com.test.sp.customer.infrastructure.output.repository.PersonRepository;
import com.test.sp.customer.infrastructure.output.repository.mapper.PersonMapper;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceAdapterImplTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonServiceAdapterImpl personServiceAdapter;

    @Test
    void savePersonWhenIsOK() {
        var request = mockCustomerRequest();
        var personEntity = mockPersonEntity();

        when(personRepository.save(any()))
                .thenReturn(Mono.just(personEntity));

        StepVerifier.create(personServiceAdapter.savePerson(request))
                .expectNext(personEntity)
                .verifyComplete();
    }

    @Test
    void savePersonReturnError() {
        var request = mockCustomerRequest();

        when(personRepository.save(any()))
                .thenReturn(Mono.error(new RuntimeException()));

        StepVerifier.create(personServiceAdapter.savePerson(request))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void updatePersonWhenIsOK() {
        var request = mockCustomerRequest();
        var customerEntity = mockCustomerEntity();
        var personEntity = mockPersonEntity();

        when(personRepository.save(any()))
                .thenReturn(Mono.just(personEntity));

        StepVerifier.create(personServiceAdapter.updatePerson(request, customerEntity))
                .expectNext(personEntity)
                .verifyComplete();
    }

    @Test
    void updatePersonReturnError() {
        var request = mockCustomerRequest();
        var customerEntity = mockCustomerEntity();

        when(personRepository.save(any()))
                .thenReturn(Mono.error(new RuntimeException()));

        StepVerifier.create(personServiceAdapter.updatePerson(request, customerEntity))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void findAllPersonsWhenIsOK() {
        var personEntity = mockPersonEntity();

        when(personRepository.findAll())
                .thenReturn(Flux.just(personEntity));

        StepVerifier.create(personServiceAdapter.findAllPersons())
                .expectNext(personEntity)
                .verifyComplete();
    }

    @Test
    void findAllPersonsReturnNotFound() {
        when(personRepository.findAll())
                .thenReturn(Flux.empty());

        StepVerifier.create(personServiceAdapter.findAllPersons())
                .expectError(PersonExceptionNotFound.class)
                .verify();
    }

    @Test
    void findByPersonIdWhenIsOK() {
        var personEntity = mockPersonEntity();

        when(personRepository.findById(any(UUID.class)))
                .thenReturn(Mono.just(personEntity));

        StepVerifier.create(personServiceAdapter.findByPersonId(UUID.randomUUID()))
                .expectNext(personEntity)
                .verifyComplete();
    }

    @Test
    void findByPersonIdReturnNotFound() {
        when(personRepository.findById(any(UUID.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(personServiceAdapter.findByPersonId(UUID.randomUUID()))
                .expectError(PersonExceptionNotFound.class)
                .verify();
    }

    @Test
    void deleteByPersonIdWhenIsOK() {
        when(personRepository.deleteById(any(UUID.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(personServiceAdapter.deleteByPersonId(UUID.randomUUID()))
                .expectNext()
                .verifyComplete();
    }

    @Test
    void deleteByPersonIdReturnError() {
        when(personRepository.deleteById(any(UUID.class)))
                .thenReturn(Mono.error(new RuntimeException()));

        StepVerifier.create(personServiceAdapter.deleteByPersonId(UUID.randomUUID()))
                .expectError(RuntimeException.class)
                .verify();
    }
}
