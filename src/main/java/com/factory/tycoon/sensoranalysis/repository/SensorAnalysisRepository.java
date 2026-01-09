package com.factory.tycoon.sensoranalysis.repository;

import com.factory.tycoon.sensoranalysis.domain.entity.SensorAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface SensorAnalysisRepository extends JpaRepository<SensorAnalysisEntity, Long> {
    List<SensorAnalysisEntity> findAllBySensor_SensorId(Long sensorId);
    Optional<SensorAnalysisEntity> findBySensor_SensorIdAndDate(Long sensorId, LocalDate date);
}
