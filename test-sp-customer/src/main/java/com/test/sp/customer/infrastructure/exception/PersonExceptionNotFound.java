package com.test.sp.customer.infrastructure.exception;

public class PersonExceptionNotFound extends ErrorException{
    public PersonExceptionNotFound() {
        super(
                "CTR-0005", "Error. Person data could not be searched within the system."
        );
    }
}
