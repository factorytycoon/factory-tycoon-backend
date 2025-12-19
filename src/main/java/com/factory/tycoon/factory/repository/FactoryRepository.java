package com.factory.tycoon.factory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.factory.tycoon.factory.domain.entity.FactoryEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface FactoryRepository extends JpaRepository<FactoryEntity, Long> {

    Optional<FactoryEntity> findByFactoryCode(String factoryCode);
    @Query(value = "SELECT factory_code FROM factory WHERE factory_code LIKE CONCAT(?1, '-%') ORDER BY factory_code DESC LIMIT 1", nativeQuery = true)
    String findMaxFactoryCodeByPrefix(String prefix);
}

