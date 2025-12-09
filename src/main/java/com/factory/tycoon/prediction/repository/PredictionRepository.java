package com.factory.tycoon.prediction.repository;

import com.factory.tycoon.prediction.domain.entity.PredictionEntity;
import com.factory.tycoon.prediction.domain.entity.PredictionLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredictionRepository extends JpaRepository<PredictionEntity, Long> {
    List<PredictionEntity> findByLevel(PredictionLevel level);
    List<PredictionEntity> findBySelected(Boolean selected);
    List<PredictionEntity> findByType(String type);
}
