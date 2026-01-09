package com.factory.tycoon.alarm.repository;

import com.factory.tycoon.alarm.domain.entity.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmEntity, Long> {

    List<AlarmEntity> findByStatus(String status);
    List<AlarmEntity> findByEquipmentId(Long equipmentId);
    List<AlarmEntity> findByEquipmentIdAndStatus(Long equipmentId, String status);
    List<AlarmEntity> findByEquipmentIdIn(List<Long> equipmentIds);

    @Query(value = "SELECT * FROM alarm WHERE sensor_dt BETWEEN :startOfDay AND :endOfDay", nativeQuery = true)
    List<AlarmEntity> findAllByCreatedAtBetween(@Param("startOfDay") String startOfDay, @Param("endOfDay") String endOfDay);

}
