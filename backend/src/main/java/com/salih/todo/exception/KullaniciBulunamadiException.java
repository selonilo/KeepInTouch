package com.salih.todo.exception;

public class KullaniciBulunamadiException extends TodoException implements IErrorCode{

    public KullaniciBulunamadiException(String name){
        super(UYGULAMA_EXCEPTION, null, new String[]{name});
    }
}
