package com.test.sp.customer.application.input.port;

import com.test.sp.customer.domain.CustomerRequest;
import com.test.sp.customer.domain.CustomerResponse;
import com.test.sp.customer.domain.GetCustomers;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Validated
public interface CustomerService {

    Mono<CustomerResponse> postCustomer(@NotNull @Valid CustomerRequest request);

    Mono<Void> putCustomer(@NotNull @NotBlank UUID customerId,
                           @NotNull @Valid CustomerRequest putCustomerByIdRequest);

    Flux<GetCustomers> getCustomers();

    Mono<Void> deleteCustomer(@NotNull @NotBlank UUID customerId);

    Mono<GetCustomers> getCustomerById(@NotNull @NotBlank UUID customerId);
}
