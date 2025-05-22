package com.sc.post.hardy.model.dto.user;

import lombok.Data;

@Data
public class TokenModel {
    private String token;
    private Long userId;
    private String name;
}
