package com.factory.tycoon.order.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {
    private Long factoryId;
    private String customer;
    private String productName;
    private Integer quantity;
    private LocalDate dueDate;
}
