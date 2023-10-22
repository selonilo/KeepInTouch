package com.salih.todo.model.mapper;
import com.salih.todo.model.dto.PostDto;
import com.salih.todo.model.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PostMapper {
    public static PostDto mapTo(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setDescription(post.getDescription());
        postDto.setUpdatedAt(post.getUpdatedAt());
        postDto.setCreatedAt(post.getCreatedAt());
        return postDto;
    }

    public static Post mapTo(PostDto postDto){
        Post post = new Post();
        post.setId(postDto.getId());
        post.setDescription(postDto.getDescription());
        post.setUpdatedAt(LocalDateTime.now());
        post.setCreatedAt(LocalDateTime.now());
        return post;
    }

    public static List<PostDto> mapToList(List<Post> dtos){
        if(dtos == null){
            return null;
        }
        return dtos.stream().map(PostMapper::mapTo).collect(Collectors.toList());
    }


}
