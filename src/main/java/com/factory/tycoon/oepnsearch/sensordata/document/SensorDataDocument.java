package com.factory.tycoon.oepnsearch.sensordata.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorDataDocument {
    
    private String id;
    
    @JsonProperty("sensor_id") // JSON의 sensor_id를 자바의 sensorId로 매핑
    private Long sensorId;
    
    private Double value;
    
    @JsonProperty("@timestamp") // 만약 JSON 필드명이 @timestamp라면 수정 필요
    private LocalDateTime timestamp;
    
    @JsonProperty("factory_id") // JSON의 factory_id를 자바의 factoryId로 매핑
    private String factoryId;
}
