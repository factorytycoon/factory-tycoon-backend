package com.factory.tycoon.inventory.repository;

import com.factory.tycoon.inventory.domain.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    List<InventoryEntity> findByFactory_FactoryId(Long factoryId);
}
