package com.test.sp.accounts.movements.application.input.port;

import com.test.sp.accounts.movements.model.GetMovementsResponse;
import com.test.sp.accounts.movements.model.PostMovementRequest;
import com.test.sp.accounts.movements.model.PostMovementResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {
    Mono<PostMovementResponse> postMovement(PostMovementRequest postMovementRequest);
    Mono<Flux<GetMovementsResponse>> getMovements();
}
