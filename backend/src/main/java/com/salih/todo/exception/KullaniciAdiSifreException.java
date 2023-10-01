package com.salih.todo.exception;

public class KullaniciAdiSifreException extends TodoException implements IErrorCode{

    public KullaniciAdiSifreException(String name){
        super(AUTH_EXCEPTION, null, new String[]{name});
    }
}
