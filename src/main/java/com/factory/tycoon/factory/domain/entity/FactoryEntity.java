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

    @Column(name = "factory_code", length = 20)
    private String factoryCode; //공장 코드

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void update(String name, String location, String description, String phone) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.phone = phone;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    } //공장코드 setter

    public void setName(String name) {
        this.name = name;
    }

}
