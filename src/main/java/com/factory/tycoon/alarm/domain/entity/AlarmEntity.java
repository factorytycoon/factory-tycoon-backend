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

    @Column(columnDefinition = "longtext")
    private String description; // 알람 설명

    @Column(length = 20)
    private String status; // OPEN, CLOSED, etc.

    @Column(length = 50)
    private String level; // yellow, orange, red

    @Column(name = "sensor_dt")
    private LocalDateTime sensorDt;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public void update(String description, String status, String level) {
        this.description = description;
        this.status = status;
        this.level = level;
    }
}
