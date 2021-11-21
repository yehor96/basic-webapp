package com.main.exceptions;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(Exception e) {
        super(e);
    }
}
