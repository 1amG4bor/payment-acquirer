package com.g4bor.payment.acquirer.exception;

public enum CustomError {
    ACCOUNT_NOT_FOUND       ("No account found with the given ID: '%s'"),
    EXISTING_ACCOUNT        ("Account is already exist with the given username: '%s'"),
    NO_WALLET_FOR_ACCOUNT   ("No wallet found in the given currency: '%s', for the account with id: '%s'"),
    WALLET_NOT_FOUND        ("No wallet found with the given ID: '%s'"),
    INVALID_CURRENCY        ("'%s' is an unsupported currency code!"),
    ALREADY_USED_CURRENCY   ("Only one wallet can exist in a given currency and there is already one in '%s' currency.");


    private final String message;

    CustomError(String message) {
        this.message = message;
    }

    public String formatMessage(String... params) {
        return String.format(this.message, params);
    }
}
