package com.factory.tycoon.alarm.repository;

import com.factory.tycoon.alarm.domain.entity.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmEntity, Long> {
<<<<<<< HEAD
    List<AlarmEntity> findBySensor_SensorId(Long sensorId);
    List<AlarmEntity> findByLevel(AlarmLevel level);
    List<AlarmEntity> findByStatus(Boolean status);
    List<AlarmEntity> findBySensor_SensorIdAndLevel(Long sensorId, AlarmLevel level);
    List<AlarmEntity> findBySensor_SensorIdAndStatus(Long sensorId, Boolean status);
    List<AlarmEntity> findAllByCreatedAtBetween(LocalDateTime startOfDay,LocalDateTime endOfDay);
=======
    List<AlarmEntity> findByStatus(String status);
    List<AlarmEntity> findByEquipmentId(Long equipmentId);
    List<AlarmEntity> findByEquipmentIdAndStatus(Long equipmentId, String status);
>>>>>>> main
}
