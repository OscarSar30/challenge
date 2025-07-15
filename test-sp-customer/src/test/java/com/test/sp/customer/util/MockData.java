package com.test.sp.customer.util;

import com.test.sp.customer.domain.CustomerRequest;
import com.test.sp.customer.domain.CustomerResponse;
import com.test.sp.customer.domain.GetCustomers;
import com.test.sp.customer.infrastructure.output.repository.entity.CustomerEntity;
import com.test.sp.customer.infrastructure.output.repository.entity.PersonEntity;
import com.test.sp.customer.model.GetCustomersResponse;
import com.test.sp.customer.model.PostCustomerRequest;
import com.test.sp.customer.model.PostCustomerResponse;
import com.test.sp.customer.model.PutCustomerByIdRequest;

import java.util.UUID;

public class MockData {

    public static final String MOCK_IDENTIFICATION = "1234567890";

    public static PostCustomerRequest mockPostCustomerRequest() {
        return new PostCustomerRequest()
                .age(20)
                .address("USA")
                .fullName("John Doe")
                .identification("1234567890")
                .password("123")
                .status(true)
                .gender("Male")
                .phone("+51999999999");
    }

    public static PostCustomerResponse mockPostCustomerResponse() {
        return new PostCustomerResponse()
                .customerId(UUID.randomUUID());
    }

    public static PutCustomerByIdRequest mockPutCustomerByIdRequest() {
        return new PutCustomerByIdRequest()
                .age(20)
                .address("EC")
                .fullName("John Doe")
                .identification("1234567890")
                .password("123")
                .status(true)
                .gender("Male")
                .phone("+51999999999");
    }

    public static GetCustomersResponse mockGetCustomersResponse() {
        return new GetCustomersResponse()
                .age(20)
                .address("EC")
                .fullName("John Doe")
                .identification("1234567890")
                .password("123")
                .status(true)
                .gender("Male")
                .phone("+51999999999");
    }

    public static CustomerEntity mockCustomerEntity() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerId(UUID.randomUUID());
        customerEntity.setPassword("123");
        customerEntity.setStatus(true);
        customerEntity.setPersonId(UUID.randomUUID());
        return customerEntity;
    }

    public static PersonEntity mockPersonEntity() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setPersonId(UUID.randomUUID());
        personEntity.setAddress("USA");
        personEntity.setAge(20);
        personEntity.setGender("Male");
        personEntity.setPhone("+51999999999");
        personEntity.setFullName("John Doe");
        personEntity.setIdentification("1234567890");
        return personEntity;
    }

    public static CustomerRequest mockCustomerRequest() {
        return new CustomerRequest("1234567890", "John Doe", "Male", 20, "USA", "+51999999999", "123", true);
    }

    public static CustomerResponse mockCustomerResponse() {
        return new CustomerResponse(UUID.randomUUID());
    }

    public static GetCustomers mockGetCustomers() {
        return new GetCustomers(UUID.randomUUID(), "1234567890", "John Doe", "Male", 20, "USA", "+51999999999", "123", true);
    }

}
