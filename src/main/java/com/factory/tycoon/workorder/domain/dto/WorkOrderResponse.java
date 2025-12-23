package com.factory.tycoon.workorder.domain.dto;

import com.factory.tycoon.workorder.domain.entity.WorkOrderEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class WorkOrderResponse {
    private Long workorderId;
    private Long equipmentId;
    private Long orderId;
    private String productName;
    private Integer targetAmount;
    private String customerName;
    private Boolean status;
    private String price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WorkOrderResponse(WorkOrderEntity workorder) {
        this.workorderId = workorder.getWorkorderId();
        this.equipmentId = workorder.getEquipmentId();
        this.orderId = workorder.getOrderId();
        this.productName = workorder.getProductName();
        this.targetAmount = workorder.getTargetAmount();
        this.customerName = workorder.getCustomerName();
        this.status = workorder.getStatus();
        this.price = workorder.getPrice();
        this.createdAt = workorder.getCreatedAt();
        this.updatedAt = workorder.getUpdatedAt();
    }
}
