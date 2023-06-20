package com.fyanes.bci.exceptions;

public class AccountAlreadyExistsException extends RuntimeException {

	public AccountAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
