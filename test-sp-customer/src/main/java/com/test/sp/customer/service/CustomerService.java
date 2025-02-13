package com.test.sp.customer.service;

import com.test.sp.customer.model.GetCustomersResponse;
import com.test.sp.customer.model.PostCustomerRequest;
import com.test.sp.customer.model.PostCustomerResponse;
import com.test.sp.customer.model.PutCustomerByIdRequest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<PostCustomerResponse> postCustomer(PostCustomerRequest request);

    Mono<Void> putCustomer(Integer customerId, PutCustomerByIdRequest putCustomerByIdRequest);

    Mono<Flux<GetCustomersResponse>> getCustomers();

    Mono<Void> deleteCustomer(Integer customerId);
}
