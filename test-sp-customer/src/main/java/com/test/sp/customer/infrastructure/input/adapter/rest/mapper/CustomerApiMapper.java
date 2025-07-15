package com.test.sp.customer.infrastructure.input.adapter.rest.mapper;

import com.test.sp.customer.domain.*;
import com.test.sp.customer.model.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        //builder = @Builder(disableBuilder = true))
public interface CustomerApiMapper {

    /*@Mapping(source = "identification", target = "identification")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "status", target = "status")*/
    CustomerRequest toCustomerRequest(PostCustomerRequest request);

    /*@Mapping(source = "customerId", target = "customerId")*/
    PostCustomerResponse toCustomerResponse(CustomerResponse response);

    /*@Mapping(source = "identification", target = "identification")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "age", target = "age")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "status", target = "status")*/
    CustomerRequest toCustomerRequest(PutCustomerByIdRequest request);

    GetCustomersResponse toGetCustomersResponse(GetCustomers getCustomers);
}
