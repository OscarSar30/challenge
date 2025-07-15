package com.test.sp.customer.infrastructure.exception;

public class PostCustomerException extends ErrorException{
    public PostCustomerException() {
        super(
                "CTR-0001", "Error, customer exists. Please contact your administrator."
        );
    }
}
