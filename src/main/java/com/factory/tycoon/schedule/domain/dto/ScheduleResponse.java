package com.factory.tycoon.schedule.domain.dto;

import com.factory.tycoon.schedule.domain.entity.ScheduleEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ScheduleResponse {
    private Long scheduleId;
    private Long workorderId;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String shift;
    private String worker;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScheduleResponse(ScheduleEntity schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.workorderId = schedule.getWorkorderId();
        this.status = schedule.getStatus();
        this.date = schedule.getDate();
        this.shift = schedule.getShift();
        this.worker = schedule.getWorker();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
