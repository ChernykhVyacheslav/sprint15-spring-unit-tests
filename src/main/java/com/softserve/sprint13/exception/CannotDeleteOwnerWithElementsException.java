package com.softserve.sprint13.exception;

public class CannotDeleteOwnerWithElementsException extends RuntimeException {
    public CannotDeleteOwnerWithElementsException(String message) {
        super(message);
    }
}