package com.test.sp.accounts.movements.infrastructure.output.adapter;

import com.test.sp.accounts.movements.api.customer.CustomerApi;
import com.test.sp.accounts.movements.application.output.port.CustomerServiceAdapter;
import com.test.sp.accounts.movements.infrastructure.exception.CustomerExceptionNotFound;
import com.test.sp.accounts.movements.model.customer.GetCustomersResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceAdapterImpl implements CustomerServiceAdapter {

    private final CustomerApi customerApi;

    @Override
    public Mono<GetCustomersResponse> verifyCustomer(UUID customerId) {
        log.info("|-> Start obtain customer information by service sp-customer");
        return customerApi.getCustomerById(customerId)
                .switchIfEmpty(Mono.error(new CustomerExceptionNotFound()))
                .doOnError(throwable -> log.error(
                        "|-> Error obtain customer by id [{}]. Error detail {}", customerId, throwable.getMessage()
                ))
                .map(getCustomersResponse -> {
                    log.info("|-> Successfully retrieved customer with ID {}", customerId);
                    return getCustomersResponse;
                });

    }
}
