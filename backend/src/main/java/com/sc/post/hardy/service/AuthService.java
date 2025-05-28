package com.sc.post.hardy.service;

import com.sc.post.hardy.model.dto.ResponseMessageModel;
import com.sc.post.hardy.model.dto.user.LoginModel;
import com.sc.post.hardy.model.dto.user.PasswordRefreshModel;
import com.sc.post.hardy.model.dto.user.TokenModel;
import com.sc.post.hardy.model.dto.user.UserModel;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {
    UserModel getById(Long id);
    UserModel register(UserModel userModel);
    UserModel updateUser(UserModel userModel);
    TokenModel login(LoginModel loginModel);
    ResponseMessageModel refreshPassword(PasswordRefreshModel passwordRefreshModel);
    String uploadUserImage(Long userId, MultipartFile file);
    void deleteUserImage(Long userId);
    String getImage(Long userId);
    void followUser(Long followUserId, Long followerUserId);
    void unFollowUser(Long followUserId, Long followerUserId);
}
