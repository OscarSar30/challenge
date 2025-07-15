package com.test.sp.accounts.movements.infrastructure.input.adapter.rest.mapper;

import com.test.sp.accounts.movements.domain.*;
import com.test.sp.accounts.movements.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import reactor.core.publisher.Flux;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMovementsApiMapper {

    AccountRequest toAccountRequest(PostAccountRequest request);

    PostAccountResponse toAccountResponse(AccountResponse response);

    @Mapping(target = "customerId", ignore = true)
    AccountRequest toAccountRequest(PutAccountByIdRequest request);

    GetAccountsResponse toGetAccountsResponse(GetAccounts getAccounts);

    MovementRequest toMovementRequest(PostMovementRequest request);

    PostMovementResponse toMovementResponse(MovementResponse response);

    GetMovementsResponse toGetMovementsResponse(GetMovements getAccounts);

    GetStatementAccountByFilterResponse toStatementAccountResponse(StatementAccount statementAccount);
}
