package com.sc.post.hardy.model.entity;

import com.sc.post.hardy.model.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMMENT_ENTITY")
public class CommentEntity extends BaseEntity {

    @Column(name = "POST_ID")
    private Long postId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "COMMENT")
    private String comment;
}
