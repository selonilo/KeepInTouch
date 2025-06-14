package com.sc.post.hardy.model.entity.base;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
@Data
@EntityListeners(BaseEntityListener.class)
public abstract class BaseEntity implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private String createdBy;
    private String updatedBy;
}
