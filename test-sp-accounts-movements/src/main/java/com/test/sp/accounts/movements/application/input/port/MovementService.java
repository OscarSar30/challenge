package com.test.sp.accounts.movements.application.input.port;

import com.test.sp.accounts.movements.domain.GetMovements;
import com.test.sp.accounts.movements.domain.MovementRequest;
import com.test.sp.accounts.movements.domain.MovementResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface MovementService {

    Mono<MovementResponse> postMovement(@NotNull @Valid MovementRequest movementRequest);

    Flux<GetMovements> getMovements();

}
