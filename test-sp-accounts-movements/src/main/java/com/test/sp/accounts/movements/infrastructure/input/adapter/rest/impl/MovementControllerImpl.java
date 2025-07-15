package com.test.sp.accounts.movements.infrastructure.input.adapter.rest.impl;

import com.test.sp.accounts.movements.api.MovementsApi;
import com.test.sp.accounts.movements.infrastructure.input.adapter.rest.mapper.AccountMovementsApiMapper;
import com.test.sp.accounts.movements.model.*;
import com.test.sp.accounts.movements.application.input.port.MovementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@AllArgsConstructor
public class MovementControllerImpl implements MovementsApi {

    private final MovementService movementService;
    private final AccountMovementsApiMapper apiMapper;

    @Override
    public Mono<ResponseEntity<PostMovementResponse>> postMovement(Mono<PostMovementRequest> postMovementRequest,
                                                                   ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the movement create method.");
        return postMovementRequest
                .map(apiMapper::toMovementRequest)
                .flatMap(movementService::postMovement)
                .map(apiMapper::toMovementResponse)
                .map(postCustomerResponse ->
                        new ResponseEntity<>(postCustomerResponse, HttpStatus.CREATED));
    }

    @Override
    public Mono<ResponseEntity<Flux<GetMovementsResponse>>> getMovements(ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the query method for all movements.");
        Flux<GetMovementsResponse> response = movementService.getMovements()
                        .map(apiMapper::toGetMovementsResponse);
        return Mono.just(ResponseEntity.ok().body(response));
    }

}
