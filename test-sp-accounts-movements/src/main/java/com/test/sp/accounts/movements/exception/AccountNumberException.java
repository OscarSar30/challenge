package com.test.sp.accounts.movements.exception;

public class AccountNumberException extends ErrorException{
    public AccountNumberException() {
        super(
                "ACC-0010", "Error, account number exists. Please contact your administrator."
        );
    }
}
