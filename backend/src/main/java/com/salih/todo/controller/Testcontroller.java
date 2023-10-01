package com.salih.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Testcontroller extends BaseController {


    @GetMapping("/api/public/get")
    public String authenticateUser() {
        return "hey";
    }

    @GetMapping("/api/get")
    public String authenticateUserr() {
        return "hey private";
    }



}