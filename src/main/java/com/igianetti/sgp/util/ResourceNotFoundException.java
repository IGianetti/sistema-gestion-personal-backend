package com.igianetti.sgp.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    // Constructor que acepta el mensaje de error.
    public ResourceNotFoundException(String message) {
        super(message);
    }
}