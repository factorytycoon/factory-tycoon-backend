package com.factory.tycoon.equipment.repository;

import com.factory.tycoon.equipment.domain.entity.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<EquipmentEntity, Long> {
    List<EquipmentEntity> findByFactory_FactoryId(Long factoryId);
    Optional<EquipmentEntity> findByName(String name);
}
