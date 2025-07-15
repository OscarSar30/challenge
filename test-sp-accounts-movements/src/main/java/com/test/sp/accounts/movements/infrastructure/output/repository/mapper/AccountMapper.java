package com.test.sp.accounts.movements.infrastructure.output.repository.mapper;

import com.test.sp.accounts.movements.domain.AccountRequest;
import com.test.sp.accounts.movements.domain.GetAccounts;
import com.test.sp.accounts.movements.infrastructure.output.repository.entity.AccountEntity;
import com.test.sp.accounts.movements.model.customer.GetCustomersResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring",
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {

	@Mapping(target = "accountId", ignore = true)
    AccountEntity requestToAccountEntity(AccountRequest request);

	@Mapping(source = "request.accountNumber", target = "accountNumber")
	@Mapping(source = "request.accountType", target = "accountType")
	@Mapping(source = "request.initialBalance", target = "initialBalance")
	@Mapping(source = "request.status", target = "status")
	@Mapping(source = "entity.customerId", target = "customerId")
    AccountEntity requestToAccountEntity(AccountRequest request,
										 AccountEntity entity);

	@Mapping(source = "entity.accountId", target = "accountId")
	@Mapping(source = "entity.accountNumber", target = "accountNumber")
	@Mapping(source = "entity.accountType", target = "accountType")
	@Mapping(source = "entity.initialBalance", target = "initialBalance")
	@Mapping(source = "entity.status", target = "status")
	@Mapping(source = "customer.fullName", target = "fullName")
	GetAccounts getAccountAll(AccountEntity entity,
							  GetCustomersResponse customer);
}