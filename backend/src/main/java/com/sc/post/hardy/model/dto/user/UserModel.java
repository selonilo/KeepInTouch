package com.sc.post.hardy.model.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sc.post.hardy.model.dto.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel extends BaseModel {
    private String name;
    private String surname;
    private String mail;
    private String password;
    private String location;
    private String imageUrl;
    private Long postCount;
    private Long followCount;
    private Long followerCount;
}
