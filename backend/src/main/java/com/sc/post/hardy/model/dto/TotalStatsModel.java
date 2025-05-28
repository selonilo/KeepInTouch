package com.sc.post.hardy.model.dto;

import com.sc.post.hardy.model.dto.base.BaseModel;
import com.sc.post.hardy.model.enums.EnumPostType;
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
public class TotalStatsModel extends BaseModel {
    private Long totalPostCount;
    private Long totalUserCount;
    private Long totalLikeCount;
    private Long totalCommentCount;
}
