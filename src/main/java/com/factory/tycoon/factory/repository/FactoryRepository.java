package com.factory.tycoon.factory.repository;

import com.factory.tycoon.factory.domain.Factory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, Long> {
}
