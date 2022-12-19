package com.example.clevertec.aspect.exception;

public class SuchRecordAlreadyExistsException extends RuntimeException{

    public SuchRecordAlreadyExistsException(String message) {
        super(message);
    }
}
