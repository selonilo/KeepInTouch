package com.sc.post.hardy.model.entity;

import com.sc.post.hardy.model.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_ENTITY")
public class UserEntity extends BaseEntity {
    @Column(name = "name",length = 60)
    private String name;

    @Column(name = "surname",length = 60)
    private String surname;

    @Column(name = "mail",length = 60,unique = true)
    private String mail;

    @Column(name = "password",length = 60)
    private String password;

    @Column(name = "location",length = 60)
    private String location;
}
