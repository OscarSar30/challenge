package com.test.sp.accounts.movements.infrastructure.output.adapter;

import com.test.sp.accounts.movements.application.output.port.CustomerServiceAdapter;
import com.test.sp.accounts.movements.infrastructure.exception.CustomerExceptionNotFound;
import com.test.sp.accounts.movements.infrastructure.input.adapter.rest.config.ApiClientProperties;
import com.test.sp.accounts.movements.model.customer.GetCustomersResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceAdapterImpl implements CustomerServiceAdapter {

    private final WebClient webClient;
    private final ApiClientProperties apiClientProperties;

    @Override
    public Mono<GetCustomersResponse> verifyCustomer(UUID customerId) {
        log.info("|-> Start obtain customer information by service sp-customer");

        return getToken()
                .flatMap(token -> {
                    String url = urlCustomerApiBuilder(customerId);
                    return webClient
                            .get()
                            .uri(url)
                            .header(HttpHeaders.AUTHORIZATION, token)
                            .retrieve()
                            .bodyToMono(GetCustomersResponse.class)
                            .switchIfEmpty(Mono.error(new CustomerExceptionNotFound()))
                            .doOnError(throwable -> log.error(
                                    "|-> Error obtain customer by id [{}]. Error detail {}", customerId, throwable.getMessage()
                            ))
                            .doOnSuccess(response -> log.info("|-> Successfully retrieved customer with ID {}", customerId));
                });

    }

    private String urlCustomerApiBuilder(UUID customerId) {
        return apiClientProperties.getClientCustomer().getUrl()
                .concat(apiClientProperties.getClientCustomer().getBasePath())
                .concat("/")
                .concat(String.valueOf(customerId));
    }

    private Mono<String> getToken() {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> "Bearer " + ctx.getAuthentication().getCredentials().toString());
    }
}
