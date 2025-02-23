package com.test.sp.customer.service.mapper;

import com.test.sp.customer.repository.entity.CustomerEntity;
import com.test.sp.customer.repository.entity.PersonEntity;
import com.test.sp.customer.model.GetCustomersResponse;
import com.test.sp.customer.model.PostCustomerRequest;
import com.test.sp.customer.model.PutCustomerByIdRequest;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {
	@Mapping(source = "request.fullName", target = "fullName")
	@Mapping(source = "request.gender", target = "gender")
	@Mapping(source = "request.age", target = "age")
	@Mapping(source = "request.address", target = "address")
	@Mapping(source = "request.phone", target = "phone")
	@Mapping(source = "request.identification", target = "identification")
	@Mapping(target = "personId", ignore = true)
	PersonEntity postRequestClientToPersonEntity(PostCustomerRequest request);

	@Mapping(source = "request.password", target = "password")
	@Mapping(source = "request.status", target = "status")
	@Mapping(source = "entity.personId", target = "personId")
	@Mapping(target = "customerId", ignore = true)
    CustomerEntity requestToCustomerEntity(PostCustomerRequest request,
                                           PersonEntity entity);

	@Mapping(source = "request.password", target = "password")
	@Mapping(source = "request.status", target = "status")
	@Mapping(source = "entity.customerId", target = "customerId")
    CustomerEntity putRequestClientToCustomerEntity(PutCustomerByIdRequest request,
                                                    CustomerEntity entity);

	@Mapping(source = "request.fullName", target = "fullName")
	@Mapping(source = "request.gender", target = "gender")
	@Mapping(source = "request.age", target = "age")
	@Mapping(source = "request.address", target = "address")
	@Mapping(source = "request.phone", target = "phone")
	@Mapping(source = "request.identification", target = "identification")
	@Mapping(source = "entity.personId", target = "personId")
	PersonEntity putRequestClientToPersonEntity(PutCustomerByIdRequest request,
												CustomerEntity entity);

	GetCustomersResponse getCustomerAll(PersonEntity entities,
										CustomerEntity entity);

}