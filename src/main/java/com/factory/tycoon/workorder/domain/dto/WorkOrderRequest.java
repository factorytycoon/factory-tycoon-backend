package com.factory.tycoon.workorder.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WorkOrderRequest {
    private Long orderId;
    private Long equipmentId;
}
