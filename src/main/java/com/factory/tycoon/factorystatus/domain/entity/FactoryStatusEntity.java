package com.factory.tycoon.factorystatus.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "factory_status")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FactoryStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String factoryId;
    private String status;
}
