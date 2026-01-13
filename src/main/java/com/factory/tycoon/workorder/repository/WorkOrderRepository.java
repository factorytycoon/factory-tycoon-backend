package com.factory.tycoon.workorder.repository;

import com.factory.tycoon.workorder.domain.entity.WorkOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;

import java.util.List;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrderEntity, Long> {
        @Query("SELECT w FROM WorkOrderEntity w JOIN OrderEntity o ON w.orderId = o.orderId WHERE o.factory.factoryId = :factoryId AND w.status = 1")
        List<WorkOrderEntity> findByFactoryIdAndStatusTrue(@Param("factoryId") Long factoryId);
    List<WorkOrderEntity> findByOrderId(Long orderId);
    List<WorkOrderEntity> findByEquipmentId(Long equipmentId);

    @Query("SELECT w FROM WorkOrderEntity w JOIN OrderEntity o ON w.orderId = o.orderId WHERE o.factory.factoryId = :factoryId")
    List<WorkOrderEntity> findByFactoryId(@Param("factoryId") Long factoryId);
    @Query("""
    SELECT new com.factory.tycoon.workorder.domain.dto.WorkOrderStatusDetailDto(
        w.productName,
        w.customerName,
        e.name,
        u.name,
        o.dueDate
    )
    FROM WorkOrderEntity w
    LEFT JOIN com.factory.tycoon.equipment.domain.entity.EquipmentEntity e ON w.equipmentId = e.equipmentId
    LEFT JOIN com.factory.tycoon.order.domain.entity.OrderEntity o ON w.orderId = o.orderId
    LEFT JOIN com.factory.tycoon.schedule.domain.entity.ScheduleEntity s ON w.workorderId = s.workorderId
    LEFT JOIN com.factory.tycoon.user.domain.entity.UserEntity u ON s.worker = CONCAT(u.userId, '')
    WHERE w.status = :status AND e.factory.factoryId = :factoryId
    """)
    List<com.factory.tycoon.workorder.domain.dto.WorkOrderStatusDetailDto> findWorkOrderDetailsByFactoryIdAndStatus(@Param("factoryId") Long factoryId, @Param("status") int status);

    @Query("SELECT w FROM WorkOrderEntity w WHERE w.createdAt >= :start AND w.createdAt < :end")
    List<WorkOrderEntity> findByCreatedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
