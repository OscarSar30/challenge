package com.test.sp.accounts.movements.infrastructure.output.repository;

import com.test.sp.accounts.movements.domain.StatementAccount;
import com.test.sp.accounts.movements.infrastructure.output.repository.entity.MovementEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface MovementRepository extends ReactiveCrudRepository<MovementEntity, UUID> {

    @Query("SELECT * FROM MOVEMENTS M " +
            "WHERE M.ACCOUNT_ID = :accountId " +
            "ORDER BY M.MOVEMENT_ID DESC LIMIT 1")
    Mono<MovementEntity> findByAccountId(UUID accountId);

    @Query("SELECT MV.DATE_MOVEMENT, PR.FULL_NAME, ACC.ACCOUNT_NUMBER, ACC.ACCOUNT_TYPE, " +
            "(MV.BALANCE - MV.AMOUNT) AS INITIAL_BALANCE, MV.AMOUNT AS AMOUNT_MOVEMENT, MV.BALANCE AS AVAILABLE_BALANCE, " +
            "ACC.STATUS FROM MOVEMENTS MV " +
            "INNER JOIN ACCOUNTS ACC ON MV.ACCOUNT_ID = ACC.ACCOUNT_ID " +
            "INNER JOIN CUSTOMER CUS ON CUS.CUSTOMER_ID = ACC.CUSTOMER_ID " +
            "INNER JOIN PERSON PR ON PR.PERSON_ID = CUS.PERSON_ID " +
            "WHERE PR.IDENTIFICATION = :identification " +
            "AND MV.DATE_MOVEMENT::DATE BETWEEN TO_DATE(:startDate, 'DD/MM/YYYY') " +
            "AND TO_DATE(:endDate, 'DD/MM/YYYY')" +
            "ORDER BY MV.DATE_MOVEMENT DESC")
    Flux<StatementAccount> findStatementAccount(String identification,
                                                String startDate,
                                                String endDate);
}
