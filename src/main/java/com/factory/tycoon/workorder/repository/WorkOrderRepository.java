package com.factory.tycoon.workorder.repository;

import com.factory.tycoon.workorder.domain.entity.WorkOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrderEntity, Long> {
    List<WorkOrderEntity> findByOrderId(Long orderId);
    List<WorkOrderEntity> findByEquipmentId(Long equipmentId);
}
