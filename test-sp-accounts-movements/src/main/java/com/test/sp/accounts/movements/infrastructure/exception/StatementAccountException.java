package com.test.sp.accounts.movements.infrastructure.exception;

public class StatementAccountException extends ErrorException{
    public StatementAccountException() {
        super(
                "ACC-0020", "Error. Bank records could not be obtained. Please try again later."
        );
    }
}
