package com.factory.tycoon.prediction.repository;

import com.factory.tycoon.prediction.domain.entity.PredictionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredictionRepository extends JpaRepository<PredictionEntity, Long> {
    List<PredictionEntity> findByUserId(Long userId);
    
    // required when you want delete method
    @Modifying
    void deleteByUserId(Long userId);
}
