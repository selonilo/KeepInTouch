package com.salih.todo.exception;

public interface IErrorCode {
    // Sistem hataları 9000 , uygulama hataları 1000 ile baslar
    int ENTITY_NOT_FOUND_EXCEPTION = 9100;
    int BAD_CREDENTIALS_EXCEPTION = 1000;
    int EMAIL_ALREADY_USED_EXCEPTION = 1002;
    int AUTH_EXCEPTION = 425;
    int UYGULAMA_EXCEPTION = 429;

}
