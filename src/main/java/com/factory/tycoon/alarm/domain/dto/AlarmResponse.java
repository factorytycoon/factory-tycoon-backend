package com.factory.tycoon.alarm.domain.dto;

import com.factory.tycoon.alarm.domain.entity.AlarmEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AlarmResponse {
    private Long alarmId;
    private Long sensorId;
    private String level;
    private String message;
    private Boolean status;
    private LocalDateTime createdAt;

    public AlarmResponse(AlarmEntity alarm) {
        this.alarmId = alarm.getAlarmId();
        this.sensorId = alarm.getSensor().getSensorId();
        this.level = alarm.getLevel() != null ? alarm.getLevel().name().toLowerCase() : null;
        this.message = alarm.getMessage();
        this.status = alarm.getStatus();
        this.createdAt = alarm.getCreatedAt();
    }
}
