package com.factory.tycoon.workorder.domain.dto;

import java.time.LocalDate;

public class WorkOrderStatusDetailDto {
    private String productName;
    private String customerName;
    private String equipmentName;
    private String workerName;
    private LocalDate dueDate;

    public WorkOrderStatusDetailDto(String productName, String customerName, String equipmentName, String workerName, LocalDate dueDate) {
        this.productName = productName;
        this.customerName = customerName;
        this.equipmentName = equipmentName;
        this.workerName = workerName;
        this.dueDate = dueDate;
    }

    public String getProductName() { return productName; }
    public String getCustomerName() { return customerName; }
    public String getEquipmentName() { return equipmentName; }
    public String getWorkerName() { return workerName; }
    public LocalDate getDueDate() { return dueDate; }
}
