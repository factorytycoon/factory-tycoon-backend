package com.factory.tycoon.factory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.factory.tycoon.factory.domain.entity.FactoryEntity;

@Repository
public interface FactoryRepository extends JpaRepository<FactoryEntity, Long> {
}
