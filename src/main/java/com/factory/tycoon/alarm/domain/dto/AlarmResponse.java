package com.factory.tycoon.alarm.domain.dto;

import com.factory.tycoon.alarm.domain.entity.AlarmEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AlarmResponse {
    private Long alarmId;
    private Long equipmentId;
    private String title;
    private String description;
    private String status;
    private String level;
    private LocalDateTime sensorDt;
    private LocalDateTime createdAt;

    public AlarmResponse(AlarmEntity alarm) {
        this.alarmId = alarm.getAlarmId();
        this.equipmentId = alarm.getEquipmentId();
        this.title = alarm.getTitle();
        this.description = alarm.getDescription();
        this.status = alarm.getStatus();
        this.level = alarm.getLevel();
        this.sensorDt = alarm.getSensorDt();
        this.createdAt = alarm.getCreatedAt();
    }
}
