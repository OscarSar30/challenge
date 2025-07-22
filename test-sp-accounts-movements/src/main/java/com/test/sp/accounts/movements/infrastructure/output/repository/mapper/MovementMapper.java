package com.test.sp.accounts.movements.infrastructure.output.repository.mapper;

import com.test.sp.accounts.movements.domain.GetMovements;
import com.test.sp.accounts.movements.domain.MovementRequest;
import com.test.sp.accounts.movements.infrastructure.output.repository.entity.AccountEntity;
import com.test.sp.accounts.movements.infrastructure.output.repository.entity.MovementEntity;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovementMapper {

	@Mapping(target = "movementId", ignore = true)
	MovementEntity requestToMovementEntity(MovementRequest request);

	@Mapping(source = "movementEntity.dateMovement", target = "dateMovement")
	@Mapping(source = "accountEntity.accountNumber", target = "accountNumber")
	@Mapping(source = "movementEntity.movementType", target = "movementType")
	@Mapping(source = "movementEntity.amount", target = "amount")
	@Mapping(source = "movementEntity.balance", target = "balance")
	GetMovements getMovementAll(MovementEntity movementEntity,
								AccountEntity accountEntity);

}