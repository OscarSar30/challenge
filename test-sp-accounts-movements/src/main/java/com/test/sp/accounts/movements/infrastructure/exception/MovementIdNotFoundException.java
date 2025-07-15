package com.test.sp.accounts.movements.infrastructure.exception;

public class MovementIdNotFoundException extends ErrorException{
    public MovementIdNotFoundException() {
        super(
                "ACC-0016", "Error. The movement was not found in the system. Please contact the administrator."
        );
    }
}
