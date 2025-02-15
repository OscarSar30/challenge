package com.test.sp.accounts.movements.service.mapper;

import com.test.sp.accounts.movements.domain.Account;
import com.test.sp.accounts.movements.domain.Movement;
import com.test.sp.accounts.movements.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring",
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovementMapper {

	@Mapping(target = "movementId", ignore = true)
    Movement postRequestClientToMovementEntity(PostMovementRequest request);

	@Mapping(source = "movement.dateMovement", target = "dateMovement")
	@Mapping(source = "account.accountNumber", target = "accountNumber")
	@Mapping(source = "movement.movementType", target = "movementType")
	@Mapping(source = "movement.amount", target = "amount")
	@Mapping(source = "movement.balance", target = "balance")
	GetMovementsResponse getMovementAll(Movement movement,
										Account account);

}