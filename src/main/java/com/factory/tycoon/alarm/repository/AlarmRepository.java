package com.factory.tycoon.alarm.repository;

import com.factory.tycoon.alarm.domain.entity.AlarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmEntity, Long> {

    List<AlarmEntity> findByStatus(String status);
    List<AlarmEntity> findByEquipmentId(Long equipmentId);
    List<AlarmEntity> findByEquipmentIdAndStatus(Long equipmentId, String status);
    List<AlarmEntity> findByEquipmentIdIn(List<Long> equipmentIds);

    @Query("SELECT a FROM AlarmEntity a WHERE a.sensorDt >= :start AND a.sensorDt < :end")
    List<AlarmEntity> findAllBySensorDtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
