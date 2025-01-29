package com.wia.implementation.exception;

public class ARLeptonSessionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ARLeptonSessionException(String message, Throwable cause) {
	super(message, cause);
    }

    public ARLeptonSessionException(String message) {
	super(message);
    }

    public ARLeptonSessionException(Throwable cause) {
	super(cause);
    }

}
