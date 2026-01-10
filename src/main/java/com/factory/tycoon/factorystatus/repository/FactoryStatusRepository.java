package com.factory.tycoon.factorystatus.repository;

import com.factory.tycoon.factorystatus.domain.entity.FactoryStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface FactoryStatusRepository extends JpaRepository<FactoryStatusEntity, Long> {
    Optional<FactoryStatusEntity> findByDate(LocalDate date);
    void deleteByDate(LocalDate date);
}