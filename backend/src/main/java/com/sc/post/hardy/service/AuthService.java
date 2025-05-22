package com.sc.post.hardy.service;

import com.sc.post.hardy.model.dto.ResponseMessageModel;
import com.sc.post.hardy.model.dto.user.LoginModel;
import com.sc.post.hardy.model.dto.user.PasswordRefreshModel;
import com.sc.post.hardy.model.dto.user.TokenModel;
import com.sc.post.hardy.model.dto.user.UserModel;

public interface AuthService {
    UserModel register(UserModel userModel);
    UserModel updateUser(UserModel userModel);
    TokenModel login(LoginModel loginModel);
    ResponseMessageModel refreshPassword(PasswordRefreshModel passwordRefreshModel);
}
