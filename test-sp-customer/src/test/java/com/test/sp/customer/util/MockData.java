package com.test.sp.customer.util;

import com.test.sp.customer.domain.Customer;
import com.test.sp.customer.domain.Person;
import com.test.sp.customer.model.GetCustomersResponse;
import com.test.sp.customer.model.PostCustomerRequest;
import com.test.sp.customer.model.PostCustomerResponse;
import com.test.sp.customer.model.PutCustomerByIdRequest;

public class MockData {

    public static final Integer MOCK_CUSTOMER_ID = 1;

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
                .customerId(1);
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

    public static Customer mockCustomerEntity() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setPersonId(1);
        return customer;
    }

    public static Person mockPersonEntity() {
        Person person = new Person();
        person.setPersonId(1);
        person.setAddress("USA");
        person.setAge(20);
        person.setGender("Male");
        person.setPhone("+51999999999");
        person.setFullName("John Doe");
        person.setIdentification("1234567890");
        return person;
    }

}
