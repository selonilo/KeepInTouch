package com.salih.todo.model.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class SignupRequest {
    private String username;

    private String email;

    private Set<String> role;

    private String password;
}
