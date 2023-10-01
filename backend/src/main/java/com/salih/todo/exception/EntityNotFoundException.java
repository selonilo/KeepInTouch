package com.salih.todo.exception;


public class EntityNotFoundException extends TodoException implements IErrorCode {

    public EntityNotFoundException(String id, Class clazz) {
        super(ENTITY_NOT_FOUND_EXCEPTION, null, new String[]{id, clazz.getSimpleName()});
    }

}