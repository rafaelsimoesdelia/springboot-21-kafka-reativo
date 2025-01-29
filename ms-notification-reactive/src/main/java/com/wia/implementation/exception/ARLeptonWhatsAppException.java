package com.wia.implementation.exception;

/**
 * Classe Exception específica do ArLepton
 */
public class ARLeptonWhatsAppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ARLeptonWhatsAppException() {
	super();
    }

    public ARLeptonWhatsAppException(String message, Throwable cause) {
	super(message, cause);
    }

    public ARLeptonWhatsAppException(String message) {
	super(message);
    }
}
