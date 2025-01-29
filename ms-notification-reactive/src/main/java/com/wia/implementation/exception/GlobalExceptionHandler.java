package com.wia.implementation.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.wia.implementation.config.Messages;
import com.wia.implementation.constants.ConstantsMessages;
import com.wia.implementation.response.GenericResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler for user validation errors.
 * 
 * @author rsdelia
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Messages messages;

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<GenericResponse<Object>> handleUserValidationException(UserValidationException ex) {
	return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new GenericResponse<>(HttpStatus.FORBIDDEN.value(),
		"User Validation Exception", ex.getMessage(), null));
    }

    @ExceptionHandler(ARLeptonException.class)
    public ResponseEntity<GenericResponse<Object>> handleARLeptonException(ARLeptonException ex) {
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse<>(
		HttpStatus.INTERNAL_SERVER_ERROR.value(), "Validation Exception", ex.getMessage(), null));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GenericResponse<Object>> handleRuntimeException(RuntimeException ex) {
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse<>(
		HttpStatus.INTERNAL_SERVER_ERROR.value(), "Runtime Exception", ex.getMessage(), null));
    }

    /**
     * Trata exceções ARLeptonSessionException.
     */
    @ExceptionHandler(ARLeptonSessionException.class)
    public ResponseEntity<GenericResponse<Object>> handleSessionException(ARLeptonSessionException ex) {
	log.error("Sessão inválida: {}", ex.getMessage());
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse<>(
		HttpStatus.UNAUTHORIZED.value(), "ARLeptonSessionException Exception", ex.getMessage(), null));

    }

    /**
     * Trata exceções de conexão com o banco de dados.
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<GenericResponse<Object>> handleDatabaseException(DataAccessException ex) {
	log.error("Exception: DataAccessException {}", ex.getMessage());
	return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
		.body(new GenericResponse<>(HttpStatus.SERVICE_UNAVAILABLE.value(),
			messages.get(ConstantsMessages.ERRO_DE_BANCO_DE_DADOS), ex.getMessage(), null));
    }

    /**
     * Trata exceções de conexão com o banco de dados.
     */
    @ExceptionHandler(JDBCConnectionException.class)
    public ResponseEntity<GenericResponse<Object>> handleJDBCConnectionException(JDBCConnectionException ex) {
	log.error("Exception JDBCConnectionException: {}", ex.getMessage());
	return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
		.body(new GenericResponse<>(HttpStatus.SERVICE_UNAVAILABLE.value(),
			messages.get(ConstantsMessages.ERRO_DE_BANCO_DE_DADOS), ex.getMessage(), null));
    }

    /**
     * Trata exceções de conexão com o banco de dados.
     */
    @ExceptionHandler(SQLServerException.class)
    public ResponseEntity<GenericResponse<Object>> handleSQLServerException(SQLServerException ex) {
	log.error("Exception SQLServerException: {}", ex.getMessage());
	return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
		.body(new GenericResponse<>(HttpStatus.SERVICE_UNAVAILABLE.value(),
			messages.get(ConstantsMessages.ERRO_DE_BANCO_DE_DADOS), ex.getMessage(), null));
    }

    /**
     * Trata exceções de conexão com o banco de dados.
     */
    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<GenericResponse<Object>> handleDataAccessResourceFailureException(
	    DataAccessResourceFailureException ex) {
	log.error("Exception DataAccessResourceFailureException: {}", ex.getMessage());
	return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
		.body(new GenericResponse<>(HttpStatus.SERVICE_UNAVAILABLE.value(),
			messages.get(ConstantsMessages.ERRO_DE_BANCO_DE_DADOS), ex.getMessage(), null));
    }

    /**
     * Trata exceções genéricas.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<Object>> handleGenericException(Exception ex) {
	log.error("Exception Exception: {}", ex.getMessage());
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse<>(
		HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno no servidor.", ex.getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	Map<String, String> errors = new HashMap<>();
	ex.getBindingResult().getFieldErrors()
		.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Handles UserNotAuthenticatedException when no user is authenticated.
     */
    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<GenericResponse<Object>> handleUserNotAuthenticatedException(
	    UserNotAuthenticatedException ex) {
	log.warn("User not authenticated: {}", ex.getMessage());
	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
		new GenericResponse<>(HttpStatus.UNAUTHORIZED.value(), "Authentication Error", ex.getMessage(), null));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<GenericResponse<String>> handleIOException(IOException ex) {
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenericResponse<>(
		HttpStatus.UNAUTHORIZED.value(), "Error processing file: ", ex.getMessage(), null));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<GenericResponse<String>> handleMaxSizeException(MaxUploadSizeExceededException ex) {
	return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
		.body(new GenericResponse<>(413, "File size exceeds limit", ex.getMessage(), null));
    }

    @ExceptionHandler(ARLeptonWhatsAppException.class)
    public ResponseEntity<GenericResponse<String>> handleARLeptonWhatsAppException(ARLeptonWhatsAppException ex) {
	return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
		.body(new GenericResponse<>(413, ex.getMessage(), ex.getMessage(), null));
    }
}