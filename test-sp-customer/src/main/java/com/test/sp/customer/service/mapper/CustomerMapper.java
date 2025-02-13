package com.test.sp.customer.service.mapper;

import com.test.sp.customer.domain.Customer;
import com.test.sp.customer.domain.Person;
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
	Person postRequestClientToPersonEntity(PostCustomerRequest request);

	@Mapping(source = "request.password", target = "password")
	@Mapping(source = "request.status", target = "status")
	@Mapping(source = "person.personId", target = "personId")
	@Mapping(target = "customerId", ignore = true)
	Customer requestToCustomerEntity(PostCustomerRequest request,
									 Person person);

	@Mapping(source = "request.password", target = "password")
	@Mapping(source = "request.status", target = "status")
	@Mapping(source = "customer.customerId", target = "customerId")
	Customer putRequestClientToCustomerEntity(PutCustomerByIdRequest request,
											  Customer customer);

	@Mapping(source = "request.fullName", target = "fullName")
	@Mapping(source = "request.gender", target = "gender")
	@Mapping(source = "request.age", target = "age")
	@Mapping(source = "request.address", target = "address")
	@Mapping(source = "request.phone", target = "phone")
	@Mapping(source = "request.identification", target = "identification")
	@Mapping(source = "entity.personId", target = "personId")
	Person putRequestClientToPersonEntity(PutCustomerByIdRequest request,
										  Customer entity);

	GetCustomersResponse getCustomerAll(Person entities,
										Customer customer);
}