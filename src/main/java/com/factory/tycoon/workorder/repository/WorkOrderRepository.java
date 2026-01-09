package com.factory.tycoon.workorder.repository;

import com.factory.tycoon.workorder.domain.entity.WorkOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrderEntity, Long> {
        @Query("SELECT w FROM WorkOrderEntity w JOIN OrderEntity o ON w.orderId = o.orderId WHERE o.factory.factoryId = :factoryId AND w.status = 1")
        List<WorkOrderEntity> findByFactoryIdAndStatusTrue(@Param("factoryId") Long factoryId);
    List<WorkOrderEntity> findByOrderId(Long orderId);
    List<WorkOrderEntity> findByEquipmentId(Long equipmentId);

    @Query("SELECT w FROM WorkOrderEntity w JOIN OrderEntity o ON w.orderId = o.orderId WHERE o.factory.factoryId = :factoryId")
    List<WorkOrderEntity> findByFactoryId(@Param("factoryId") Long factoryId);
}
