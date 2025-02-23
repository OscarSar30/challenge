package com.test.sp.accounts.movements.exception;

public class AccountInsufficientBalance extends ErrorException{
    public AccountInsufficientBalance() {
        super(
                "ACC-0019", "Error. The account does not have the value you wish to withdraw. Please try again."
        );
    }
}
