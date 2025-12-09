package com.factory.tycoon.schedule.service;

import com.factory.tycoon.schedule.domain.dto.ScheduleRequest;
import com.factory.tycoon.schedule.domain.dto.ScheduleResponse;
import com.factory.tycoon.schedule.domain.entity.ScheduleEntity;
import com.factory.tycoon.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public List<ScheduleResponse> getSchedules() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ScheduleResponse createSchedule(ScheduleRequest request) {
        ScheduleEntity schedule = ScheduleEntity.builder()
                .workorderId(request.getWorkorderId())
                .status(request.getStatus())
                .date(request.getDate())
                .shift(request.getShift())
                .worker(request.getWorker())
                .build();
        ScheduleEntity savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponse(savedSchedule);
    }

    public ScheduleResponse getSchedule(Long id) {
        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + id));
        return new ScheduleResponse(schedule);
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long id, ScheduleRequest request) {
        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + id));
        schedule.update(request.getStatus(), request.getDate(), request.getShift(), request.getWorker());
        return new ScheduleResponse(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id) {
        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + id));
        scheduleRepository.delete(schedule);
    }

    public List<ScheduleResponse> getSchedulesByWorkorderId(Long workorderId) {
        return scheduleRepository.findByWorkorderId(workorderId).stream()
                .map(ScheduleResponse::new)
                .collect(Collectors.toList());
    }
}
