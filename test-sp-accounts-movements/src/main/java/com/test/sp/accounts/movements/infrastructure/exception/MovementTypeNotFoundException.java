package com.test.sp.accounts.movements.infrastructure.exception;

public class MovementTypeNotFoundException extends ErrorException{
    public MovementTypeNotFoundException() {
        super(
                "ACC-0013", "Error. Movement not recognized within the system."
        );
    }
}
