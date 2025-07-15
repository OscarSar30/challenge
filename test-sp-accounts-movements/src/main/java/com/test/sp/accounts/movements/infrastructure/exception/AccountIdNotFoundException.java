package com.test.sp.accounts.movements.infrastructure.exception;

public class AccountIdNotFoundException extends ErrorException{
    public AccountIdNotFoundException() {
        super(
                "ACC-0012", "Error. The account was not found in the system. Please contact the administrator."
        );
    }
}
