package com.wia.implementation.exception;

/**
 * Custom exception to handle errors during stored procedure execution. Provides
 * a more descriptive exception type for database-related operations.
 * 
 * @author rs
 */
public class StoredProcedureExecutionException extends RuntimeException {

    private static final long serialVersionUID = -4960020198287478817L;

    /**
     * Constructs a new StoredProcedureExecutionException with the specified detail
     * message.
     *
     * @param message the detail message.
     */
    public StoredProcedureExecutionException(String message) {
	super(message);
    }

    /**
     * Constructs a new StoredProcedureExecutionException with the specified detail
     * message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public StoredProcedureExecutionException(String message, Throwable cause) {
	super(message, cause);
    }
}
