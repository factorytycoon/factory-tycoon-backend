package com.factory.tycoon.alarm.domain.entity;

import com.factory.tycoon.sensor.domain.entity.SensorEntity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", nullable = false)
    private SensorEntity sensor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AlarmLevel level;

    private String message;

    @Column(nullable = false)
    private Boolean status; // true: solved, false: not solved

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public void update(AlarmLevel level, String message, Boolean status) {
        this.level = level;
        this.message = message;
        this.status = status;
    }

    public void resolve() {
        this.status = true;
    }
}
