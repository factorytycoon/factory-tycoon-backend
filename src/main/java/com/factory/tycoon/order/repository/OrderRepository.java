package com.factory.tycoon.order.repository;

import com.factory.tycoon.order.domain.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByCustomerContaining(String customer);
    List<OrderEntity> findByFactory_FactoryId(Long factoryId);
}
