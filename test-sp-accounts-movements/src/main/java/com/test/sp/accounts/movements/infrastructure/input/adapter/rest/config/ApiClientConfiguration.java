package com.test.sp.accounts.movements.infrastructure.input.adapter.rest.config;

import com.test.sp.accounts.movements.api.customer.CustomerApi;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApiClientConfiguration {

    private final ApiClientProperties apiClientProperties;

    @NotNull
    @Bean
    public CustomerApi customerApi() {
        final var customerApi = new CustomerApi();
        customerApi.getApiClient().setBasePath(apiClientProperties.getClientCustomer().getUrl());
        return customerApi;
    }

}
