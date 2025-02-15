package com.test.sp.accounts.movements.exception;

public class InsufficientBalanceException extends ErrorException{
    public InsufficientBalanceException() {
        super(
                "ACC-0015", "Error. The account has no available balance. Please make a deposit."
        );
    }
}
