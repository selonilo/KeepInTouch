package com.sc.post.hardy.model.dto.post;

import com.sc.post.hardy.model.enums.EnumPostType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostQueryModel {
    private String title;
    private EnumPostType postType;
}
