package com.main.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Exception e) {
        super(e);
    }
}
