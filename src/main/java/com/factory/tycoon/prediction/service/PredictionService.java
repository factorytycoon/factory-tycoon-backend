package com.factory.tycoon.prediction.service;

import com.factory.tycoon.prediction.domain.dto.PredictionRequest;
import com.factory.tycoon.prediction.domain.dto.PredictionResponse;
import com.factory.tycoon.prediction.domain.entity.PredictionEntity;
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

    public List<PredictionResponse> getAllPredictions() {
        return predictionRepository.findAll().stream()
                .map(PredictionResponse::new)
                .collect(Collectors.toList());
    }

    public List<PredictionResponse> getPredictionsByUser(Long userId) {
        return predictionRepository.findByUserId(userId).stream()
                .map(PredictionResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PredictionResponse createPrediction(PredictionRequest request) {
        PredictionEntity prediction = PredictionEntity.builder()
                .userId(request.getUserId())
                .description(request.getDescription())
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
        prediction.updateDescription(request.getDescription());
        return new PredictionResponse(prediction);
    }

    @Transactional
    public void deletePrediction(Long id) {
        PredictionEntity prediction = predictionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Prediction not found with id: " + id));
        predictionRepository.delete(prediction);
    }

    @Transactional
    public void deletePredictionsByUser(Long userId) {
        predictionRepository.deleteByUserId(userId);
    }
}
