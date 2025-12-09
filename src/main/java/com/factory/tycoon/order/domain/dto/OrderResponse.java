package com.factory.tycoon.order.domain.dto;

import com.factory.tycoon.order.domain.entity.OrderEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Long factoryId;
    private String customer;
    private String productName;
    private Integer quantity;
    private LocalDate dueDate;
    private LocalDateTime createdAt;

    public OrderResponse(OrderEntity order) {
        this.orderId = order.getOrderId();
        this.factoryId = order.getFactory().getFactoryId();
        this.customer = order.getCustomer();
        this.productName = order.getProductName();
        this.quantity = order.getQuantity();
        this.dueDate = order.getDueDate();
        this.createdAt = order.getCreatedAt();
    }
}
