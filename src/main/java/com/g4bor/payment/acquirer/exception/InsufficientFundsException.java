package com.g4bor.payment.acquirer.exception;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super("Insufficient Funds Available. The wallet does not have the required funds to complete the transaction.");
    }

    public InsufficientFundsException(String message) {
        super(message);
    }
}
