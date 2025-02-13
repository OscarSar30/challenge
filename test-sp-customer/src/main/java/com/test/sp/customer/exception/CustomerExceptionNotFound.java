package com.test.sp.customer.exception;

public class CustomerExceptionNotFound extends ErrorException{
    public CustomerExceptionNotFound() {
        super(
                "CTR-0002", "Error. Client was not found within the system."
        );
    }
}
