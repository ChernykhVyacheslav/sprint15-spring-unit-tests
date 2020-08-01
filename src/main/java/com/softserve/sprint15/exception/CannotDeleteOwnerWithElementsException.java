package com.softserve.sprint15.exception;

public class CannotDeleteOwnerWithElementsException extends RuntimeException {
    public CannotDeleteOwnerWithElementsException(String message) {
        super(message);
    }
}