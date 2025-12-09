package com.factory.tycoon.factory.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "factory")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class FactoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long factoryId;

    @Column(nullable = false)
    private String name;

    private String location;

    private String description;

    private String phone;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void update(String name, String location, String description, String phone) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.phone = phone;
    }
}
