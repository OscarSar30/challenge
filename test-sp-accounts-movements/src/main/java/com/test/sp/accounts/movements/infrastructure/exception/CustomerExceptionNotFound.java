package com.test.sp.accounts.movements.infrastructure.exception;

public class CustomerExceptionNotFound extends ErrorException{
    public CustomerExceptionNotFound() {
        super(
                "ACC-0011", "Error. Client was not found within the system."
        );
    }
}
