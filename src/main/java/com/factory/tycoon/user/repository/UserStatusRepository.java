package com.factory.tycoon.user.repository;

import com.factory.tycoon.user.domain.entity.UserStatus;
import com.factory.tycoon.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
        @Query("SELECT us.user.userId FROM UserStatus us WHERE us.date = :date AND us.status = true")
        List<Long> findWorkingUserIdsByDate(@Param("date") LocalDate date);
    @Query("SELECT us.user FROM UserStatus us WHERE us.date = :date AND us.status = false")
    List<UserEntity> findUnavailableUsersByDate(@Param("date") LocalDate date);
}
