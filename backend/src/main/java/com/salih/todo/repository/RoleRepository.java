package com.salih.todo.repository;


import java.util.Optional;

import com.salih.todo.model.entity.Role;
import com.salih.todo.model.type.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRole name);
}