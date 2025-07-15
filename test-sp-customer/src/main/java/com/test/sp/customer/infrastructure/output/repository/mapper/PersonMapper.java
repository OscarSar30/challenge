package com.test.sp.customer.infrastructure.output.repository.mapper;

import com.test.sp.customer.domain.CustomerRequest;
import com.test.sp.customer.infrastructure.output.repository.entity.CustomerEntity;
import com.test.sp.customer.infrastructure.output.repository.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PersonMapper {
    @Mapping(source = "request.fullName", target = "fullName")
    @Mapping(source = "request.gender", target = "gender")
    @Mapping(source = "request.age", target = "age")
    @Mapping(source = "request.address", target = "address")
    @Mapping(source = "request.phone", target = "phone")
    @Mapping(source = "request.identification", target = "identification")
    @Mapping(target = "personId", ignore = true)
    PersonEntity requestToEntity(CustomerRequest request);

    @Mapping(source = "request.fullName", target = "fullName")
    @Mapping(source = "request.gender", target = "gender")
    @Mapping(source = "request.age", target = "age")
    @Mapping(source = "request.address", target = "address")
    @Mapping(source = "request.phone", target = "phone")
    @Mapping(source = "request.identification", target = "identification")
    @Mapping(source = "entity.personId", target = "personId")
    PersonEntity requestToEntity(CustomerRequest request,
                                 CustomerEntity entity);

}