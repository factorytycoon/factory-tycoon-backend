package com.factory.tycoon.user.repository;

import com.factory.tycoon.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndUserIdNot(String email, Long userId);
    
    List<UserEntity> findByRole(@Param("role") com.factory.tycoon.user.domain.entity.UserRole role);

    List<UserEntity> findByFactory_FactoryIdAndRole(Long factoryId, com.factory.tycoon.user.domain.entity.UserRole role);

}
