package com.wia.implementation.exception;

/**
 * Exception thrown when no data found.
 * 
 * @author rsdelia
 */
public class NoDataFoundException extends RuntimeException {
    private static final long serialVersionUID = -1703068761386837651L;

    public NoDataFoundException(String message) {
	super(message);
    }
}
