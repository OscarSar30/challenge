package com.test.sp.customer.infrastructure.output.repository.mapper;

import com.test.sp.customer.domain.CustomerRequest;
import com.test.sp.customer.domain.GetCustomers;
import com.test.sp.customer.infrastructure.output.repository.entity.CustomerEntity;
import com.test.sp.customer.infrastructure.output.repository.entity.PersonEntity;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {

    @Mapping(source = "request.password", target = "password")
    @Mapping(source = "request.status", target = "status")
    @Mapping(source = "entity.personId", target = "personId")
    @Mapping(target = "customerId", ignore = true)
    CustomerEntity requestToCustomerEntity(CustomerRequest request,
                                           PersonEntity entity);

    @Mapping(source = "request.password", target = "password")
    @Mapping(source = "request.status", target = "status")
    @Mapping(source = "entity.customerId", target = "customerId")
    CustomerEntity requestToCustomerEntity(CustomerRequest request,
                                           CustomerEntity entity);

    GetCustomers getCustomerAll(PersonEntity entities,
                                CustomerEntity entity);

}