package com.sc.post.hardy.controller;

import com.sc.post.hardy.model.dto.ResponseMessageModel;
import com.sc.post.hardy.model.dto.user.LoginModel;
import com.sc.post.hardy.model.dto.user.PasswordRefreshModel;
import com.sc.post.hardy.model.dto.user.TokenModel;
import com.sc.post.hardy.model.dto.user.UserModel;
import com.sc.post.hardy.service.AuthService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("/get/{id}")
    public ResponseEntity<UserModel> getById(@PathVariable(name = "id") @NotNull Long id) {
        return ResponseEntity.ok(authService.getById(id));
    }

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
