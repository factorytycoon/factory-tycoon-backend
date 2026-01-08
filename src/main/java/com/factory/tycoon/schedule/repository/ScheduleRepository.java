package com.factory.tycoon.schedule.repository;

import com.factory.tycoon.schedule.domain.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findByWorkorderId(Long workorderId);

    @Query("SELECT s FROM ScheduleEntity s JOIN com.factory.tycoon.workorder.domain.entity.WorkOrderEntity w ON s.workorderId = w.workorderId WHERE s.date = :date AND s.shift = :shift AND w.equipmentId = :equipmentId")
    List<ScheduleEntity> findByDateAndShiftAndWorkorderId(@Param("date") LocalDate date, @Param("shift") String shift, @Param("equipmentId") Long equipmentId);
}
