package com.wia.implementation.exception;

/**
 * Custom exception for file processing errors.
 */
public class FileProcessingException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 4074355832081623299L;

    public FileProcessingException(String message, Throwable cause) {
	super(message, cause);
    }

    public FileProcessingException(String string) {
	super(string);
    }
}
