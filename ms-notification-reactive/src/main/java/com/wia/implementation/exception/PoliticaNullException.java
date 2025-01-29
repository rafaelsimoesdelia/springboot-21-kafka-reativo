package com.wia.implementation.exception;

/**
 * Exceção específica.
 */
public class PoliticaNullException extends RuntimeException {

    private static final long serialVersionUID = -3750648470210784635L;

    public PoliticaNullException() {
	super("O campo IdPolitica não pode ser nulo.");
    }
}
