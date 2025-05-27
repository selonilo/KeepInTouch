package com.sc.post.hardy.repository;

import com.sc.post.hardy.model.entity.LikeEntity;
import com.sc.post.hardy.model.entity.ViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViewRepository extends JpaRepository<ViewEntity, Long> {
    Optional<ViewEntity> findByPostIdAndUserId(Long postId, Long userId);
    long countByPostId(Long postId);
}
