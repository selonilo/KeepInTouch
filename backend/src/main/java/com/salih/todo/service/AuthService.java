package com.salih.todo.service;


import com.salih.todo.exception.KullaniciAdiSifreException;
import com.salih.todo.exception.TokenRefreshException;
import com.salih.todo.exception.UserNotFoundException;
import com.salih.todo.model.entity.RefreshToken;
import com.salih.todo.model.entity.Role;
import com.salih.todo.model.entity.User;
import com.salih.todo.model.mapper.AuthMapper;
import com.salih.todo.model.type.UserRole;
import com.salih.todo.repository.RefreshTokenRepository;
import com.salih.todo.repository.RoleRepository;
import com.salih.todo.repository.UserRepository;
import com.salih.todo.security.jwt.JwtUtils;
import com.salih.todo.security.refresh.RefreshTokenService;
import com.salih.todo.security.services.UserDetailsImpl;
import com.salih.todo.model.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;

    private boolean checkStringIsEmptyBlankOrNull(String string) {
        return string == null || string.isEmpty() || string.trim().isEmpty();
    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {

        String authUsername;

        if (!checkStringIsEmptyBlankOrNull(loginRequest.getUsername())) {
            authUsername = loginRequest.getUsername();
        } else if (!checkStringIsEmptyBlankOrNull(loginRequest.getEmail())) {
            Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());
            if (optionalUser.isPresent()) {
                authUsername = optionalUser.get().getUsername();
            } else {
                throw new UserNotFoundException("Email");
            }
        } else {
            throw new KullaniciAdiSifreException(loginRequest.getUsername());
        }

        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authUsername, loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new KullaniciAdiSifreException(loginRequest.getUsername());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(
                "Bearer " + jwt,
                refreshToken.getToken(),
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(), roles));
    }

    public UserDto save(UserDto userDto) {
        User user = AuthMapper.mapTo(userDto);
        userRepository.save(user);
        return userDto;
    }

    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail());
        if (signUpRequest.getRole() != null){
            Set<String> strRoles = signUpRequest.getRole();

            Set<Role> roles = new HashSet<>();


            if (strRoles == null) {
                Role userRole = roleRepository.findByName(UserRole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(UserRole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);
                            break;

                        default:
                            Role userRole = roleRepository.findByName(UserRole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                    }
                });
            }

            user.setRoles(roles);
        }
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }


    public ResponseEntity<?> refreshtoken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    public ResponseEntity<?> logoutUser(LogOutRequest logOutRequest) {
        refreshTokenService.deleteByUserId(logOutRequest.getUserId());
        return ResponseEntity.ok("Log out successful!");
    }

    public Boolean delete(Long id) {
        List<RefreshToken> refreshTokens = refreshTokenRepository.findByUserId(id);
        for (RefreshToken refreshToken : refreshTokens) {
            refreshToken.setUser(null);
        }
        refreshTokenRepository.saveAll(refreshTokens);
        userRepository.deleteById(id);
        return true;
    }

    public List<UserDto> getAll() {
        return AuthMapper.mapToList(userRepository.findAll());
    }

    public UserDto update(UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(userDto.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            AuthMapper.updateUser(user, userDto);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("not found id");
        }
        return userDto;
    }

}
