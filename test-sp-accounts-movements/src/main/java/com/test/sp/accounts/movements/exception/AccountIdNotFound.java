package com.test.sp.accounts.movements.exception;

public class AccountIdNotFound extends ErrorException{
    public AccountIdNotFound() {
        super(
                "ACC-0012", "Error. Account was not found within the system."
        );
    }
}
