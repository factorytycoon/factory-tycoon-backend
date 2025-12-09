package com.factory.tycoon.schedule.repository;

import com.factory.tycoon.schedule.domain.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findByWorkorderId(Long workorderId);
}
