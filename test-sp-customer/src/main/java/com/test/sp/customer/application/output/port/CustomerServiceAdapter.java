package com.test.sp.customer.application.output.port;

import com.test.sp.customer.domain.CustomerRequest;
import com.test.sp.customer.infrastructure.output.repository.entity.CustomerEntity;
import com.test.sp.customer.infrastructure.output.repository.entity.PersonEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CustomerServiceAdapter {

    Mono<Boolean> verifyCustomer(@NotNull @NotBlank String identification);

    Mono<CustomerEntity> saveCustomer(@NotNull @Valid CustomerRequest request,
                                      PersonEntity personEntity);

    Mono<CustomerEntity> findByCustomerId(@NotNull @NotBlank UUID customerId);

    Mono<CustomerEntity> updateCustomer(@NotNull @Valid CustomerRequest request,
                                        CustomerEntity customerEntity);

    Mono<CustomerEntity> findByPersonId(UUID personId);

    Mono<Void> deleteByCustomerId(@NotNull @NotBlank UUID customerId);
}
