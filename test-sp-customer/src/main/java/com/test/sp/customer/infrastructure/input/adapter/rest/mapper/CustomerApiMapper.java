package com.test.sp.customer.infrastructure.input.adapter.rest.mapper;

import com.test.sp.customer.domain.*;
import com.test.sp.customer.model.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerApiMapper {

    CustomerRequest toCustomerRequest(PostCustomerRequest request);

    PostCustomerResponse toCustomerResponse(CustomerResponse response);

    CustomerRequest toCustomerRequest(PutCustomerByIdRequest request);

    GetCustomersResponse toGetCustomersResponse(GetCustomers getCustomers);
}
