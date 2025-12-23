package com.factory.tycoon.workorder.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkOrderRequest {
    private Long equipmentId;
    private Long orderId;
    private String productName;
    private Integer targetAmount;
    private String customerName;
    private Boolean status;
    private String price;
}
