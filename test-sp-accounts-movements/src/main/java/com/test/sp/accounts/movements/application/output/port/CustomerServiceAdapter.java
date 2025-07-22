package com.test.sp.accounts.movements.application.output.port;

import com.test.sp.accounts.movements.model.customer.GetCustomersResponse;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CustomerServiceAdapter {

    Mono<GetCustomersResponse> verifyCustomer (@NotNull UUID customerId);

}
