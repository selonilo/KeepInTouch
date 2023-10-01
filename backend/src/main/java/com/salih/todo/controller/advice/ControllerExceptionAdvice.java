package com.salih.todo.controller.advice;

import com.salih.todo.exception.TodoExceptionAdvice;
import com.salih.todo.exception.ExceptionUtil;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionAdvice extends TodoExceptionAdvice {

    public ControllerExceptionAdvice(final ExceptionUtil exceptionUtil) {
        super(exceptionUtil);
    }

}
