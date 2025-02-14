package com.test.sp.accounts.movements.service;

import com.test.sp.accounts.movements.model.customer.GetCustomersResponse;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<GetCustomersResponse> verifyCustomer (Integer customerId);

}
