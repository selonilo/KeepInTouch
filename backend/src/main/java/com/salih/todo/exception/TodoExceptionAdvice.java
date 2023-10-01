package com.salih.todo.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

public class TodoExceptionAdvice {

    private final ExceptionUtil exceptionUtil;

    @Value("${spring.application.name}")
    private String applicationName;

    public TodoExceptionAdvice(final ExceptionUtil exceptionUtil) {
        this.exceptionUtil = exceptionUtil;
    }

    @ExceptionHandler(TodoException.class)
    public ResponseEntity handleException(TodoException exception, HttpServletRequest request) {
        return new ResponseEntity<>(exceptionUtil.convert(exception, getLanguageFormRequestHeader(request), applicationName), exception.getHttpStatus());
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<String> handleRestClientException(HttpStatusCodeException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new String(ex.getResponseBodyAsByteArray(), StandardCharsets.UTF_8));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex, HttpServletRequest request) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Servis tarafında bir hata oluştu, gönderdiğiniz istekte bazı alanlar null olabilir");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleNullPointerException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Parse edilemeyen bir field yolladınız");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Hatalı bilgi girildi");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Bu bilgilere sahip kullanıcı bulunamadı");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Servis tarafında bir hata oluştu" + ex.getMessage());
    }

    private String getLanguageFormRequestHeader(HttpServletRequest request) {
        Enumeration<String> languageRanges = request.getHeaders("Accept-Language");
        if (languageRanges.hasMoreElements()) {
            return languageRanges.nextElement();
        }
        return null;
    }
}

