package com.test.sp.accounts.movements.infrastructure.input.adapter.rest.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "services.client.api")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiClientProperties {
    @NotNull
    @Valid
    private HttpApiClient clientCustomer;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Validated
    public static class HttpApiClient {
        @NotBlank
        private String url;
        @NotBlank
        private String basePath;
    }
}
