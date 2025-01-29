package com.wia.implementation.exception;

/**
 * Exceção específica.
 */
public class FlagAceiteNullException extends RuntimeException {

    private static final long serialVersionUID = -3750648470210784635L;

    public FlagAceiteNullException() {
	super("O campo Flag de Aceite de Política é obrigatório e aceita apenas os valores '0' ou '1'.");
    }
}
