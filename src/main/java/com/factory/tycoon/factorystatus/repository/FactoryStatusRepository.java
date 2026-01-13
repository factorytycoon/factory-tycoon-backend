package com.factory.tycoon.factorystatus.repository;

import com.factory.tycoon.factorystatus.domain.entity.FactoryStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Repository
public interface FactoryStatusRepository extends JpaRepository<FactoryStatusEntity, Long> {
    Optional<FactoryStatusEntity> findByDate(LocalDate date);
    
    // factoryId를 포함한 조회 (중요!)
    Optional<FactoryStatusEntity> findByDateAndFactoryId(LocalDate date, String factoryId);
    
    // 날짜 범위 조회 (차트용)
    List<FactoryStatusEntity> findByDateBetweenAndFactoryIdOrderByDateAsc(
        LocalDate startDate, LocalDate endDate, String factoryId);
    
    void deleteByDate(LocalDate date);
}