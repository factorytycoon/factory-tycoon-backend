
package com.factory.tycoon.workorder.service;

import com.factory.tycoon.workorder.domain.dto.WorkOrderRequest;
import com.factory.tycoon.workorder.domain.dto.WorkOrderResponse;
import com.factory.tycoon.workorder.domain.entity.WorkOrderEntity;
import com.factory.tycoon.workorder.repository.WorkOrderRepository;
import com.factory.tycoon.order.domain.entity.OrderEntity;
import com.factory.tycoon.order.repository.OrderRepository;
import com.factory.tycoon.equipment.domain.entity.EquipmentEntity;
import com.factory.tycoon.equipment.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkOrderService {
        public List<WorkOrderResponse> getActiveWorkOrdersByFactoryId(Long factoryId) {
            return workorderRepository.findByFactoryIdAndStatusTrue(factoryId).stream()
                    .map(WorkOrderResponse::new)
                    .collect(Collectors.toList());
        }
    public List<WorkOrderResponse> getWorkOrdersByFactoryId(Long factoryId) {
        return workorderRepository.findByFactoryId(factoryId).stream()
                .map(WorkOrderResponse::new)
                .collect(Collectors.toList());
    }

    private final WorkOrderRepository workorderRepository;
    private final OrderRepository orderRepository;
    private final EquipmentRepository equipmentRepository;

    public List<WorkOrderResponse> getWorkOrders() {
        return workorderRepository.findAll().stream()
                .map(WorkOrderResponse::new)
                .collect(Collectors.toList());
    }

        @Transactional
        public WorkOrderResponse createWorkOrder(WorkOrderRequest request) {
        // orderId로 order 조회
        OrderEntity order = orderRepository.findById(request.getOrderId())
            .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + request.getOrderId()));
        // equipmentId로 equipment 조회 (존재 확인만)
        equipmentRepository.findById(request.getEquipmentId())
            .orElseThrow(() -> new IllegalArgumentException("Equipment not found with id: " + request.getEquipmentId()));

        WorkOrderEntity workorder = WorkOrderEntity.builder()
            .equipmentId(request.getEquipmentId())
            .orderId(request.getOrderId())
            .productName(order.getProductName())
            .targetAmount(order.getQuantity())
            .customerName(order.getCustomer())
            .status(1) // 기본값 1 (진행중)
            .price(null)
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
        // orderId로 order 조회
        OrderEntity order = orderRepository.findById(request.getOrderId())
            .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + request.getOrderId()));
        // equipmentId로 equipment 조회 (존재 확인만)
        equipmentRepository.findById(request.getEquipmentId())
            .orElseThrow(() -> new IllegalArgumentException("Equipment not found with id: " + request.getEquipmentId()));

        // order의 필드로 workorder 값 갱신
        workorder.setOrderId(request.getOrderId());
        workorder.setEquipmentId(request.getEquipmentId());
        workorder.setProductName(order.getProductName());
        workorder.setTargetAmount(order.getQuantity());
        workorder.setCustomerName(order.getCustomer());

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
    @Transactional
    public WorkOrderResponse updateWorkOrderStatus(Long id, int status) {
        WorkOrderEntity workorder = workorderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("WorkOrder not found with id: " + id));
        workorder.setStatus(status);
        return new WorkOrderResponse(workorder);
    }
    
    @Transactional
    public void updateWorkOrderStatusByDate(Long factoryId, String selectedDate) {
        List<WorkOrderEntity> workorders = workorderRepository.findByFactoryId(factoryId);
        for (WorkOrderEntity workorder : workorders) {
            if (workorder.getStatus() == 2) continue;
            OrderEntity order = orderRepository.findById(workorder.getOrderId())
                .orElse(null);
            if (order == null || order.getDueDate() == null) continue;
            java.time.LocalDate dueDate = order.getDueDate();
            java.time.LocalDate selDate = java.time.LocalDate.parse(selectedDate);
            if (!selDate.isAfter(dueDate)) {
                workorder.setStatus(1); // 진행중
            } else {
                workorder.setStatus(0); // 지연
            }
        }
    }
}
