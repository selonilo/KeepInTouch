package com.sc.post.hardy.repository;

import com.sc.post.hardy.model.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByPostIdAndUserId(Long postId, Long userId);
    long countByPostId(Long postId);
}
