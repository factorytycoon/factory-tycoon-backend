package com.factory.tycoon.sensor.repository;

import com.factory.tycoon.sensor.domain.entity.SensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<SensorEntity, Long> {
    List<SensorEntity> findByEquipment_EquipmentId(Long equipmentId);
}
