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

import com.factory.tycoon.workorder.repository.WorkOrderRepository;
import com.factory.tycoon.equipment.repository.EquipmentRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final WorkOrderRepository workOrderRepository;
    private final EquipmentRepository equipmentRepository;


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
