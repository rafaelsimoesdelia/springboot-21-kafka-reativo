package com.wia.implementation.exception;

/**
 * Classe Exception específica do ArLepton
 */
public class ARLeptonException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ARLeptonException() {
	super();
    }

    public ARLeptonException(String message, Throwable cause) {
	super(message, cause);
    }

    public ARLeptonException(String message) {
	super(message);
    }
}
