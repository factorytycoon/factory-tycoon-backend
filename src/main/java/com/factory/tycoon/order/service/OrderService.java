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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final FactoryRepository factoryRepository;

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
}
