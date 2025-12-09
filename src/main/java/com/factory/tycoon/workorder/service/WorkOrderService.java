package com.factory.tycoon.workorder.service;

import com.factory.tycoon.workorder.domain.dto.WorkOrderRequest;
import com.factory.tycoon.workorder.domain.dto.WorkOrderResponse;
import com.factory.tycoon.workorder.domain.entity.WorkOrderEntity;
import com.factory.tycoon.workorder.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkOrderService {

    private final WorkOrderRepository workorderRepository;

    public List<WorkOrderResponse> getWorkOrders() {
        return workorderRepository.findAll().stream()
                .map(WorkOrderResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public WorkOrderResponse createWorkOrder(WorkOrderRequest request) {
        WorkOrderEntity workorder = WorkOrderEntity.builder()
                .equipmentId(request.getEquipmentId())
                .orderId(request.getOrderId())
                .productName(request.getProductName())
                .targetAmount(request.getTargetAmount())
                .build();
        WorkOrderEntity savedWorkOrder = workorderRepository.save(workorder);
        return new WorkOrderResponse(savedWorkOrder);
    }

    public WorkOrderResponse getWorkOrder(Long id) {
        WorkOrderEntity workorder = workorderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("WorkOrder not found with id: " + id));
        return new WorkOrderResponse(workorder);
    }

    @Transactional
    public WorkOrderResponse updateWorkOrder(Long id, WorkOrderRequest request) {
        WorkOrderEntity workorder = workorderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("WorkOrder not found with id: " + id));
        workorder.update(request.getProductName(), request.getTargetAmount());
        return new WorkOrderResponse(workorder);
    }

    @Transactional
    public void deleteWorkOrder(Long id) {
        WorkOrderEntity workorder = workorderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("WorkOrder not found with id: " + id));
        workorderRepository.delete(workorder);
    }

    public List<WorkOrderResponse> getWorkOrdersByOrderId(Long orderId) {
        return workorderRepository.findByOrderId(orderId).stream()
                .map(WorkOrderResponse::new)
                .collect(Collectors.toList());
    }
}
