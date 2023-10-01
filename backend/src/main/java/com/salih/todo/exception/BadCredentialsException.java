package com.salih.todo.exception;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends TodoException implements IErrorCode {

    public BadCredentialsException() {
        super(BAD_CREDENTIALS_EXCEPTION, null, null, null, null, HttpStatus.BAD_REQUEST);
    }

}