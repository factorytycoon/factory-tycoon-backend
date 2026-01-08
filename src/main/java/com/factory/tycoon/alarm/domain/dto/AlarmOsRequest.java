package com.factory.tycoon.alarm.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class AlarmOsRequest {
    private String monitor_name;
    private String trigger_name;
    private String severity;
    private List<Hit> hits;
    
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Hit {
        private String device_id;
        private String sensor_time;
        private Map<String, Object> full_data;
    }
}
