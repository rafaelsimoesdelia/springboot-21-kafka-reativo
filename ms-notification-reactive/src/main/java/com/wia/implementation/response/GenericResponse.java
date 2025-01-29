package com.wia.implementation.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic response wrapper to standardize API responses.
 *
 * @param <T> the type of the data payload.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {
    private int status;
    private String error;
    private String message;
    private T data;
}
