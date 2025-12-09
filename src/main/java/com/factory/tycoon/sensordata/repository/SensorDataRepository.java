package com.factory.tycoon.sensordata.repository;

import com.factory.tycoon.sensordata.domain.entity.SensorDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorDataEntity, Long> {
    List<SensorDataEntity> findBySensor_SensorId(Long sensorId);
    List<SensorDataEntity> findBySensor_SensorIdAndDate(Long sensorId, LocalDate date);
}
