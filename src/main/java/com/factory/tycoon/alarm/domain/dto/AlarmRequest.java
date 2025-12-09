package com.factory.tycoon.alarm.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlarmRequest {
    private Long sensorId;
    private String level;
    private String message;
    private Boolean status;
}
