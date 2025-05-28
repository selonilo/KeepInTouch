package com.sc.post.hardy.repository;

import com.sc.post.hardy.model.entity.FollowEntity;
import com.sc.post.hardy.model.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    Optional<LikeEntity> findByFollowUserIdAndFollowerUserId(Long followUserId, Long followerUserId);
}
