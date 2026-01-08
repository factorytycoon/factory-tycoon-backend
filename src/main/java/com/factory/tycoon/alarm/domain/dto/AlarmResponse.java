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
    private String monitorName;
    private String triggerName;
    private String sensorSnapshot; // JSON 문자열
    private String status;
    private LocalDateTime sensorDt;
    private LocalDateTime createdAt;

    public AlarmResponse(AlarmEntity alarm) {
        this.alarmId = alarm.getAlarmId();
        this.equipmentId = alarm.getEquipmentId();
        this.monitorName = alarm.getMonitorName();
        this.triggerName = alarm.getTriggerName();
        this.sensorSnapshot = alarm.getSensorSnapshot();
        this.status = alarm.getStatus();
        this.sensorDt = alarm.getSensorDt();
        this.createdAt = alarm.getCreatedAt();
    }
}
