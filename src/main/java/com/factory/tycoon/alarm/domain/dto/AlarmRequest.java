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
    private String description;
    private String status;
    private String level;
    private LocalDateTime sensorDt;
}
