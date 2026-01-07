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
import java.time.LocalDate;

import com.factory.tycoon.workorder.repository.WorkOrderRepository;
import com.factory.tycoon.equipment.repository.EquipmentRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    public List<String> findWorkerByDateShift(Long workorderId, LocalDate date, String shift) {
        // workorderId로 equipmentId 조회
        var workorder = workOrderRepository.findById(workorderId).orElse(null);
        if (workorder == null) return List.of();
        Long equipmentId = workorder.getEquipmentId();
        if (equipmentId == null) return List.of();

        // schedule에서 date, shift, equipmentId로 조회
        List<Long> workerIds = scheduleRepository.findByDateAndShiftAndWorkorderId(date, shift, equipmentId).stream()
            .map(sch -> {
                try {
                    return Long.parseLong(sch.getWorker());
                } catch (NumberFormatException e) {
                    return null;
                }
            })
            .filter(id -> id != null)
            .distinct()
            .collect(Collectors.toList());

        // userId로 user name 조회 (userRepository.findById 반복 호출)
        return workerIds.stream()
            .map(id -> userRepository.findById(id).map(user -> user.getName()).orElse(null))
            .filter(name -> name != null)
            .collect(Collectors.toList());
    }

    private final ScheduleRepository scheduleRepository;
    private final WorkOrderRepository workOrderRepository;
    private final EquipmentRepository equipmentRepository;
    private final com.factory.tycoon.user.repository.UserRepository userRepository;


    public List<ScheduleResponse> getSchedules() {
        return scheduleRepository.findAll().stream()
                .map(schedule -> {
                    Long workorderId = schedule.getWorkorderId();
                    Long equipmentId = null;
                    String equipmentName = null;
                    if (workorderId != null) {
                        var workorder = workOrderRepository.findById(workorderId).orElse(null);
                        if (workorder != null) {
                            equipmentId = workorder.getEquipmentId();
                            if (equipmentId != null) {
                                var equipment = equipmentRepository.findById(equipmentId).orElse(null);
                                if (equipment != null) {
                                    equipmentName = equipment.getName();
                                }
                            }
                        }
                    }
                    return new ScheduleResponse(schedule, equipmentId, equipmentName);
                })
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

        // equipment 정보도 포함해서 반환
        Long workorderId = savedSchedule.getWorkorderId();
        Long equipmentId = null;
        String equipmentName = null;
        if (workorderId != null) {
            var workorder = workOrderRepository.findById(workorderId).orElse(null);
            if (workorder != null) {
                equipmentId = workorder.getEquipmentId();
                if (equipmentId != null) {
                    var equipment = equipmentRepository.findById(equipmentId).orElse(null);
                    if (equipment != null) {
                        equipmentName = equipment.getName();
                    }
                }
            }
        }
        return new ScheduleResponse(savedSchedule, equipmentId, equipmentName);
    }


    public ScheduleResponse getSchedule(Long id) {
        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + id));
        Long workorderId = schedule.getWorkorderId();
        Long equipmentId = null;
        String equipmentName = null;
        if (workorderId != null) {
            var workorder = workOrderRepository.findById(workorderId).orElse(null);
            if (workorder != null) {
                equipmentId = workorder.getEquipmentId();
                if (equipmentId != null) {
                    var equipment = equipmentRepository.findById(equipmentId).orElse(null);
                    if (equipment != null) {
                        equipmentName = equipment.getName();
                    }
                }
            }
        }
        return new ScheduleResponse(schedule, equipmentId, equipmentName);
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long id, ScheduleRequest request) {
        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + id));
        schedule.update(request.getStatus(), request.getDate(), request.getShift(), request.getWorker());

        // equipment 정보도 포함해서 반환
        Long workorderId = schedule.getWorkorderId();
        Long equipmentId = null;
        String equipmentName = null;
        if (workorderId != null) {
            var workorder = workOrderRepository.findById(workorderId).orElse(null);
            if (workorder != null) {
                equipmentId = workorder.getEquipmentId();
                if (equipmentId != null) {
                    var equipment = equipmentRepository.findById(equipmentId).orElse(null);
                    if (equipment != null) {
                        equipmentName = equipment.getName();
                    }
                }
            }
        }
        return new ScheduleResponse(schedule, equipmentId, equipmentName);
    }

    @Transactional
    public void deleteSchedule(Long id) {
        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with id: " + id));
        scheduleRepository.delete(schedule);
    }

    public List<ScheduleResponse> getSchedulesByWorkorderId(Long workorderId) {
        return scheduleRepository.findByWorkorderId(workorderId).stream()
                .map(schedule -> {
                    Long equipmentId = null;
                    String equipmentName = null;
                    var workorder = workOrderRepository.findById(workorderId).orElse(null);
                    if (workorder != null) {
                        equipmentId = workorder.getEquipmentId();
                        if (equipmentId != null) {
                            var equipment = equipmentRepository.findById(equipmentId).orElse(null);
                            if (equipment != null) {
                                equipmentName = equipment.getName();
                            }
                        }
                    }
                    return new ScheduleResponse(schedule, equipmentId, equipmentName);
                })
                .collect(Collectors.toList());
    }
}
