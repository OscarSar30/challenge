package com.test.sp.customer.infrastructure.exception;

public class CustomerExceptionNotFound extends ErrorException{
    public CustomerExceptionNotFound() {
        super(
                "CTR-0002", "Error. Customer was not found within the system."
        );
    }
}
