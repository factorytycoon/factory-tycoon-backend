package com.factory.tycoon.sensor.domain.entity;

import com.factory.tycoon.equipment.domain.entity.EquipmentEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "sensor")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SensorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sensorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private EquipmentEntity equipment;

    @Column(name = "`key`", nullable = false)
    private String key;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private String unit;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public void update(String key, String label, String unit) {
        this.key = key;
        this.label = label;
        this.unit = unit;
    }
}
