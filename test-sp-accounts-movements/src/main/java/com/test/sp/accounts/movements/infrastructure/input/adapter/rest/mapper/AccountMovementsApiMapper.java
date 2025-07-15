package com.test.sp.accounts.movements.infrastructure.input.adapter.rest.mapper;

import com.test.sp.accounts.movements.domain.AccountRequest;
import com.test.sp.accounts.movements.domain.AccountResponse;
import com.test.sp.accounts.movements.domain.GetAccounts;
import com.test.sp.accounts.movements.model.GetAccountsResponse;
import com.test.sp.accounts.movements.model.PostAccountRequest;
import com.test.sp.accounts.movements.model.PostAccountResponse;
import com.test.sp.accounts.movements.model.PutAccountByIdRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMovementsApiMapper {

    AccountRequest toAccountRequest(PostAccountRequest request);

    PostAccountResponse toAccountResponse(AccountResponse response);

    @Mapping(target = "customerId", ignore = true)
    AccountRequest toAccountRequest(PutAccountByIdRequest request);

    GetAccountsResponse toGetAccountsResponse(GetAccounts getAccounts);

}
