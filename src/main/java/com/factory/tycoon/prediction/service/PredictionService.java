package com.factory.tycoon.prediction.service;

import com.factory.tycoon.prediction.domain.dto.PredictionRequest;
import com.factory.tycoon.prediction.domain.dto.PredictionResponse;
import com.factory.tycoon.prediction.domain.entity.PredictionEntity;
import com.factory.tycoon.prediction.domain.entity.PredictionLevel;
import com.factory.tycoon.prediction.repository.PredictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PredictionService {

    private final PredictionRepository predictionRepository;

    public List<PredictionResponse> getAllPredictions(String type, String level, Boolean selected) {
        List<PredictionEntity> predictions = predictionRepository.findAll();

        if (type != null) {
            predictions = predictions.stream()
                    .filter(p -> p.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        }

        if (level != null) {
            PredictionLevel predictionLevel = PredictionLevel.from(level);
            predictions = predictions.stream()
                    .filter(p -> p.getLevel() == predictionLevel)
                    .collect(Collectors.toList());
        }

        if (selected != null) {
            predictions = predictions.stream()
                    .filter(p -> p.getSelected().equals(selected))
                    .collect(Collectors.toList());
        }

        return predictions.stream()
                .map(PredictionResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PredictionResponse createPrediction(PredictionRequest request) {
        PredictionEntity prediction = PredictionEntity.builder()
                .factoryId(request.getFactoryId())
                .type(request.getType())
                .level(PredictionLevel.from(request.getLevel()))
                .message(request.getMessage())
                .selected(request.getSelected() != null ? request.getSelected() : false)
                .build();
        PredictionEntity savedPrediction = predictionRepository.save(prediction);
        return new PredictionResponse(savedPrediction);
    }

    public PredictionResponse getPrediction(Long id) {
        PredictionEntity prediction = predictionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prediction not found with id: " + id));
        return new PredictionResponse(prediction);
    }

    @Transactional
    public PredictionResponse updatePrediction(Long id, PredictionRequest request) {
        PredictionEntity prediction = predictionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prediction not found with id: " + id));
        prediction.updateSelected(request.getSelected());
        return new PredictionResponse(prediction);
    }

    @Transactional
    public void deletePrediction(Long id) {
        PredictionEntity prediction = predictionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prediction not found with id: " + id));
        predictionRepository.delete(prediction);
    }
}
