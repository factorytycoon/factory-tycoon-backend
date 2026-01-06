package com.factory.tycoon.schedule.repository;

import com.factory.tycoon.schedule.domain.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDate;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findByWorkorderId(Long workorderId);

    // equipmentId, date, shift로 worker 조회
    List<ScheduleEntity> findByDateAndShiftAndWorkorderId(LocalDate date, String shift, Long workorderId);
}
