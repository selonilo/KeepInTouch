package com.sc.post.hardy.model.dto.post;

import com.sc.post.hardy.model.dto.base.BaseModel;
import com.sc.post.hardy.model.dto.user.UserModel;
import com.sc.post.hardy.model.enums.EnumPostType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentModel extends BaseModel {
    private Long postId;
    private Long userId;
    private String comment;
    private UserModel userModel;
}
