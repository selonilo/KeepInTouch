package com.sc.post.hardy.controller;

import com.sc.post.hardy.model.dto.ResponseMessageModel;
import com.sc.post.hardy.model.dto.user.LoginModel;
import com.sc.post.hardy.model.dto.user.PasswordRefreshModel;
import com.sc.post.hardy.model.dto.user.TokenModel;
import com.sc.post.hardy.model.dto.user.UserModel;
import com.sc.post.hardy.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@RequestBody UserModel userModel) {
        return ResponseEntity.ok(authService.register(userModel));
    }

    @PutMapping("/update")
    public ResponseEntity<UserModel> updateUser(@RequestBody UserModel userModel) {
        return ResponseEntity.ok(authService.updateUser(userModel));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenModel> login(@RequestBody LoginModel loginModel) {
        return ResponseEntity.ok(authService.login(loginModel));
    }

    @PostMapping("/refreshPassword")
    public ResponseEntity<ResponseMessageModel> refreshPassword(@RequestBody PasswordRefreshModel passwordRefreshModel) {
        return ResponseEntity.ok(authService.refreshPassword(passwordRefreshModel));
    }
}
