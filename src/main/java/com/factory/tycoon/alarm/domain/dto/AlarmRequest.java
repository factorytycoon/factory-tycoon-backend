package com.factory.tycoon.alarm.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AlarmRequest {
    private Long equipmentId;
    private String monitorName;
    private String triggerName;
    private String sensorSnapshot; // JSON 문자열
    private String status;
    private LocalDateTime sensorDt;
}
