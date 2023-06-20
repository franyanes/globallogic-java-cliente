package com.fyanes.bci.exceptions;

public class PasswordEncryptionErrorException extends RuntimeException {

	public PasswordEncryptionErrorException(String errorMessage) {
        super(errorMessage);
    }
}
