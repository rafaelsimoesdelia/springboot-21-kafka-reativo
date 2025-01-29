package com.wia.implementation.exception;

/**
 * Exception thrown when user login validation fails.
 * 
 * @author rsdelia
 */
public class UserValidationException extends RuntimeException {
    private static final long serialVersionUID = -1703068761386837651L;

    public UserValidationException(String message) {
	super(message);
    }
}
