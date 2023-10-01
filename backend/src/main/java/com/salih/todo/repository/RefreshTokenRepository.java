package com.salih.todo.repository;

import java.util.List;
import java.util.Optional;

import com.salih.todo.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    List<RefreshToken> findByUserId(Long id);

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    @Query(value = "DELETE FROM RefreshToken t WHERE t.user.id = :userId ")
    int deleteByUserId(@Param(value = "userId") Long userId);


}
