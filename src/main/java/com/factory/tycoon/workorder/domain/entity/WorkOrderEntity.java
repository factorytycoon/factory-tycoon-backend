package com.factory.tycoon.workorder.domain.entity;

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
@Table(name = "workorder")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class WorkOrderEntity {
    // ...existing code...

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workorderId;

    @Column(nullable = false)
    private Long equipmentId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer targetAmount;

    @Column(name = "customer_name")
    private String customerName;

    @Builder.Default
    @Column(nullable = false)
    private Boolean status = false;

    @Column
    private String price;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void update(String productName, Integer targetAmount) {
        this.productName = productName;
        this.targetAmount = targetAmount;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setTargetAmount(Integer targetAmount) {
        this.targetAmount = targetAmount;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
}
