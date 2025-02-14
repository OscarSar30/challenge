package com.test.sp.accounts.movements.service.mapper;

import com.test.sp.accounts.movements.domain.Account;
import com.test.sp.accounts.movements.model.GetAccountsResponse;
import com.test.sp.accounts.movements.model.PostAccountRequest;
import com.test.sp.accounts.movements.model.PutAccountByIdRequest;
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
	Account postRequestClientToAccountEntity(PostAccountRequest request);

	@Mapping(source = "request.accountNumber", target = "accountNumber")
	@Mapping(source = "request.accountType", target = "accountType")
	@Mapping(source = "request.initialBalance", target = "initialBalance")
	@Mapping(source = "request.status", target = "status")
	@Mapping(source = "account.customerId", target = "customerId")
	@Mapping(source = "account.accountId", target = "accountId")
	Account putRequestClientToAccount(PutAccountByIdRequest request,
									  Account account);

	@Mapping(source = "account.accountNumber", target = "accountNumber")
	@Mapping(source = "account.accountType", target = "accountType")
	@Mapping(source = "account.initialBalance", target = "initialBalance")
	@Mapping(source = "account.status", target = "status")
	@Mapping(source = "customer.fullName", target = "fullName")
	GetAccountsResponse getAccountAll(Account account,
									  GetCustomersResponse customer);
}