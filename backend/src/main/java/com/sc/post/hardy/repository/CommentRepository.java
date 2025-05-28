package com.sc.post.hardy.repository;

import com.sc.post.hardy.model.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByPostId(Long postId);
    List<CommentEntity> findByPostIdAndUserId(Long postId, Long userId);
    long countByUserId(Long userId);
}
