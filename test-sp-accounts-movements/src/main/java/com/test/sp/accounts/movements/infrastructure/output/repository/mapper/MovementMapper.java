package com.test.sp.accounts.movements.infrastructure.output.repository.mapper;

import com.test.sp.accounts.movements.infrastructure.output.repository.entity.AccountEntity;
import com.test.sp.accounts.movements.infrastructure.output.repository.entity.MovementEntity;
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
	MovementEntity postRequestClientToMovementEntity(PostMovementRequest request);

	@Mapping(source = "movementEntity.dateMovement", target = "dateMovement")
	@Mapping(source = "accountEntity.accountNumber", target = "accountNumber")
	@Mapping(source = "movementEntity.movementType", target = "movementType")
	@Mapping(source = "movementEntity.amount", target = "amount")
	@Mapping(source = "movementEntity.balance", target = "balance")
	GetMovementsResponse getMovementAll(MovementEntity movementEntity,
										AccountEntity accountEntity);

}