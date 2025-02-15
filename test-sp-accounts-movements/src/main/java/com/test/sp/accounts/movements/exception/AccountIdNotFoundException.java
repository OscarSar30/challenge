package com.test.sp.accounts.movements.exception;

public class AccountIdNotFoundException extends ErrorException{
    public AccountIdNotFoundException() {
        super(
                "ACC-0012", "Error. The account was not found in the system or has a zero balance. Please contact the administrator."
        );
    }
}
