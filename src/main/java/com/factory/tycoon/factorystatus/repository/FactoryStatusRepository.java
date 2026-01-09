package com.factory.tycoon.factorystatus.repository;

import com.factory.tycoon.factorystatus.domain.entity.FactoryStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface FactoryStatusRepository extends JpaRepository<FactoryStatusEntity, Long> {
    // Renamed from getDailyFactoryStatusFromRDB to match the 'date' property
    List<FactoryStatusEntity> findAllByDate(LocalDate date);
    
    @Query("SELECT f FROM FactoryStatusEntity f WHERE f.date = :date")
    List<FactoryStatusEntity> getDailyFactoryStatusFromRDB(@Param("date") LocalDate date);

}