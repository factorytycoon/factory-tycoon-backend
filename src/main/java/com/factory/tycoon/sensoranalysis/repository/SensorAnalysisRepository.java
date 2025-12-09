package com.factory.tycoon.sensoranalysis.repository;

import com.factory.tycoon.sensoranalysis.domain.entity.SensorAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorAnalysisRepository extends JpaRepository<SensorAnalysisEntity, Long> {
    Optional<SensorAnalysisEntity> findBySensorData_SensorDataId(Long sensorDataId);
    List<SensorAnalysisEntity> findAllBySensorData_Sensor_SensorId(Long sensorId);
}
