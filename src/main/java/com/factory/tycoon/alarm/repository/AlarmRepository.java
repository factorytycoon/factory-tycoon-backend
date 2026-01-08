package com.factory.tycoon.alarm.repository;

import com.factory.tycoon.alarm.domain.entity.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmEntity, Long> {
    List<AlarmEntity> findBySensor_SensorId(Long sensorId);
    List<AlarmEntity> findByStatus(String status);
    List<AlarmEntity> findBySensor_SensorIdAndStatus(Long sensorId, Boolean status);
}
