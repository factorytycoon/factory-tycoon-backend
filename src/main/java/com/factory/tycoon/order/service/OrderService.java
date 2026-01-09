package com.factory.tycoon.order.service;

import com.factory.tycoon.factory.domain.entity.FactoryEntity;
import com.factory.tycoon.factory.repository.FactoryRepository;
import com.factory.tycoon.order.domain.dto.OrderRequest;
import com.factory.tycoon.order.domain.dto.OrderResponse;
import com.factory.tycoon.order.domain.entity.OrderEntity;
import com.factory.tycoon.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import com.factory.tycoon.workorder.domain.entity.WorkOrderEntity;
import com.factory.tycoon.workorder.repository.WorkOrderRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class OrderService {

    private final OrderRepository orderRepository;
    private final FactoryRepository factoryRepository;
    private final WorkOrderRepository workOrderRepository;


    public List<OrderResponse> getFilteredOrdersByFactory(Long factoryId, String selectedDate) {
    java.time.LocalDate selDate = java.time.LocalDate.parse(selectedDate);
    // 해당 factory의 workorder 중 status=2인 orderId 조회
    List<WorkOrderEntity> workorders = workOrderRepository.findByFactoryId(factoryId);
    java.util.Set<Long> excludeOrderIds = workorders.stream()
        .filter(w -> w.getStatus() == 2)
        .map(WorkOrderEntity::getOrderId)
        .collect(java.util.stream.Collectors.toSet());
    // factory의 모든 order 조회
    List<OrderEntity> orders = orderRepository.findByFactory_FactoryId(factoryId);
    // status2인 order와 due_date가 selectedDate 이전인 order 제외 후 변환
    return orders.stream()
        .filter(order -> !excludeOrderIds.contains(order.getOrderId()))
        .filter(order -> order.getDueDate() != null && !selDate.isAfter(order.getDueDate()))
        .map(OrderResponse::new)
        .collect(java.util.stream.Collectors.toList());
    }

    public List<OrderResponse> getAllOrders(String customer) {
        List<OrderEntity> orders;
        if (customer != null && !customer.isEmpty()) {
            orders = orderRepository.findByCustomerContaining(customer);
        } else {
            orders = orderRepository.findAll();
        }
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        FactoryEntity factory = factoryRepository.findById(request.getFactoryId())
                .orElseThrow(() -> new IllegalArgumentException("Factory not found with id: " + request.getFactoryId()));

        OrderEntity order = OrderEntity.builder()
                .factory(factory)
                .customer(request.getCustomer())
                .productName(request.getProductName())
                .quantity(request.getQuantity())
                .dueDate(request.getDueDate())
                .build();
        OrderEntity saved = orderRepository.save(order);
        return new OrderResponse(saved);
    }

    public OrderResponse getOrder(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        return new OrderResponse(order);
    }

    @Transactional
    public OrderResponse updateOrder(Long id, OrderRequest request) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        order.update(request.getCustomer(), request.getProductName(), request.getQuantity(), request.getDueDate());
        return new OrderResponse(order);
    }

    @Transactional
    public void deleteOrder(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
        orderRepository.delete(order);
    }

    public List<OrderResponse> getOrdersByFactoryId(Long factoryId) {
        return orderRepository.findByFactory_FactoryId(factoryId).stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }
}
