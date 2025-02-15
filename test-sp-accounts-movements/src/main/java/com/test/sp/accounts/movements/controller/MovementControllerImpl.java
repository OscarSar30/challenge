package com.test.sp.accounts.movements.controller;

import com.test.sp.accounts.movements.api.MovementsApi;
import com.test.sp.accounts.movements.model.*;
import com.test.sp.accounts.movements.service.MovementService;
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

    @Override
    public Mono<ResponseEntity<PostMovementResponse>> postMovement(Mono<PostMovementRequest> postMovementRequest,
                                                                   ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the movement create method.");
        return postMovementRequest
                .flatMap(movementService::postMovement)
                .map(postCustomerResponse ->
                        new ResponseEntity<>(postCustomerResponse, HttpStatus.CREATED));
    }

    @Override
    public Mono<ResponseEntity<Flux<GetMovementsResponse>>> getMovements(ServerWebExchange exchange) {
        log.info("|-> Initiates the call to the query method for all movements.");
        return movementService.getMovements()
                .map(getMovementsResponse ->
                        new ResponseEntity<>(getMovementsResponse, HttpStatus.OK));
    }

}
