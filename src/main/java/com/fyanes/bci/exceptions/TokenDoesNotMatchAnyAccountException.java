package com.fyanes.bci.exceptions;

public class TokenDoesNotMatchAnyAccountException extends RuntimeException {

	public TokenDoesNotMatchAnyAccountException(String errorMessage) {
        super(errorMessage);
    }
}
