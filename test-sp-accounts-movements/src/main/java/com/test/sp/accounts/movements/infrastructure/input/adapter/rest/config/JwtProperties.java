package com.test.sp.accounts.movements.infrastructure.input.adapter.rest.config;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtProperties {

    @NotNull
    private String secret;

}