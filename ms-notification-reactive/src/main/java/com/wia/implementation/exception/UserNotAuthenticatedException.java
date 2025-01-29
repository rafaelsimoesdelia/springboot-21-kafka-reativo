package com.wia.implementation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception thrown when the user is not authenticated.
 * 
 * @author rsdelia
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotAuthenticatedException extends RuntimeException {

    private static final long serialVersionUID = 5636158749995237582L;

    public UserNotAuthenticatedException(String message) {
	super(message);
    }
}
