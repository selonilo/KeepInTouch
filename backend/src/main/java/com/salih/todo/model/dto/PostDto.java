package com.salih.todo.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostDto {

    private Long id;
    private String description;
    private Long userId;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private String username;
}
