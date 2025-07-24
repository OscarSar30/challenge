package com.test.sp.customer.infrastructure.input.adapter.rest.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.sp.customer.model.ErrorResponse;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                Claims claims = jwtUtil.getClaims(token);
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        claims.getSubject(), null, List.of());

                return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
            } else {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                ErrorResponse errorResponse = new ErrorResponse()
                        .code(String.valueOf(exchange.getResponse().getStatusCode().value()))
                        .detail(HttpStatus.UNAUTHORIZED.getReasonPhrase());

                byte[] bytes;
                try {
                    bytes = new ObjectMapper().writeValueAsBytes(errorResponse);
                } catch (JsonProcessingException ex) {
                    bytes = ("{\"code\":\"500\",\"detail\":\"Error serializing error response\"}").getBytes(StandardCharsets.UTF_8);
                }
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                return exchange.getResponse().writeWith(Mono.just(buffer));
            }
        }
        return chain.filter(exchange);
    }
}