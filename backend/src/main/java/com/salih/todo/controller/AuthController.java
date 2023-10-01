package com.salih.todo.controller;

import com.salih.todo.service.AuthService;
import com.salih.todo.model.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Authorization", description = "Web için login ve register servisleri")
@RestController
@RequiredArgsConstructor
public class AuthController extends BaseController {
    private final AuthService authService;

    @PostMapping(AUTH_LOGIN)
    @Operation(summary = "Giriş yapma servisi, email zorunludur")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping(AUTH_REGISTER)
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }

    @PostMapping(AUTH_REFRESH_TOKEN)
    public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return authService.refreshtoken(tokenRefreshRequest);
    }

    @PostMapping(PUBLIC_AUTH_SAVE)
    public ResponseEntity<UserDto> save(@RequestBody UserDto user){
        return ResponseEntity.ok(authService.save(user));
    }

    @PostMapping(AUTH_LOGOUT)
    public ResponseEntity<?> logoutUser(@RequestBody LogOutRequest logOutRequest) {
        return authService.logoutUser(logOutRequest);
    }

    @DeleteMapping(PUBLIC_AUTH_DELETE+"/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable(value = "id",required = true) Long id){
        return ResponseEntity.ok(authService.delete(id));

    }

    @GetMapping(PUBLIC_AUTH_GETALL)
    public List<UserDto> getAll(){
        return authService.getAll();
    }

    @PostMapping(PUBLIC_AUTH_UPDATE)
    public ResponseEntity<UserDto> update(@RequestBody UserDto user){
        return ResponseEntity.ok(authService.update(user));
    }


}
