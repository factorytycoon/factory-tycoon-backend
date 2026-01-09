package com.factory.tycoon.alarm.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "alarm")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AlarmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    @Column(name = "equipment_id", nullable = false)
    private Long equipmentId;

    @Column(name = "monitor_name", length = 100)
    private String monitorName;

    @Column(name = "trigger_name", length = 100)
    private String triggerName;

    @Column(name = "sensor_snapshot", columnDefinition = "longtext")
    private String sensorSnapshot; // JSON 문자열

    @Column(length = 20)
    private String status; // OPEN, CLOSED, etc.

    @Column(name = "sensor_dt")
    private LocalDateTime sensorDt;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public void update(String monitorName, String triggerName, String sensorSnapshot, String status) {
        this.monitorName = monitorName;
        this.triggerName = triggerName;
        this.sensorSnapshot = sensorSnapshot;
        this.status = status;
    }
}
