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
@Table(name = "VIEW_ENTITY", uniqueConstraints = @UniqueConstraint(columnNames = {"USER_ID", "POST_ID"}))
public class ViewEntity extends BaseEntity {

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "POST_ID")
    private Long postId;
}
