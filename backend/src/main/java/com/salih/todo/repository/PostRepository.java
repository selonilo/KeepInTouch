package com.salih.todo.repository;

import com.salih.todo.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> getByUserId(Long id);
}
