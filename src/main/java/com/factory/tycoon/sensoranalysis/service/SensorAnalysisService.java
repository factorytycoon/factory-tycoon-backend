package com.factory.tycoon.sensoranalysis.service;

import com.factory.tycoon.sensoranalysis.domain.entity.SensorAnalysisEntity;
import com.factory.tycoon.sensordata.domain.entity.SensorDataEntity;
import com.factory.tycoon.sensordata.repository.SensorDataRepository;
import com.factory.tycoon.sensoranalysis.domain.dto.SensorAnalysisRequest;
import com.factory.tycoon.sensoranalysis.domain.dto.SensorAnalysisResponse;
import com.factory.tycoon.sensoranalysis.repository.SensorAnalysisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SensorAnalysisService {

    private final SensorAnalysisRepository sensorAnalysisRepository;
    private final SensorDataRepository sensorDataRepository;

    public List<SensorAnalysisResponse> getAllSensorAnalysis() {
        return sensorAnalysisRepository.findAll().stream()
                .map(SensorAnalysisResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public SensorAnalysisResponse createSensorAnalysis(SensorAnalysisRequest request) {
    
        SensorDataEntity sensorData = sensorDataRepository.findById(request.getSensorDataId())
                .orElseThrow(() -> new IllegalArgumentException("SensorData not found with id: " + request.getSensorDataId()));

        List<BigDecimal> sensorValues = request.getSensorValues();
        
        sensorAnalysisRepository.findBySensor_SensorIdAndDate(sensorData.getSensor().getSensorId(), sensorData.getDate())
                .ifPresent(entity -> {
                    sensorAnalysisRepository.delete(entity);
                    sensorAnalysisRepository.flush();
                });

        BigDecimal max = sensorValues.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        BigDecimal min = sensorValues.stream().min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        BigDecimal avg = sensorValues.isEmpty() ? BigDecimal.ZERO : sensorValues.stream().reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(sensorValues.size()), 2, RoundingMode.HALF_UP);

        SensorAnalysisEntity sensorAnalysis = SensorAnalysisEntity.builder()
                .sensor(sensorData.getSensor())
                .date(sensorData.getDate())
                .maxValue(max)
                .minValue(min)
                .avgValue(avg)
                .build();
        SensorAnalysisEntity saved = sensorAnalysisRepository.save(sensorAnalysis);
        return new SensorAnalysisResponse(saved);
    }

    public SensorAnalysisResponse getSensorAnalysis(Long id) {
        SensorAnalysisEntity sensorAnalysis = sensorAnalysisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("SensorAnalysis not found with id: " + id));
        return new SensorAnalysisResponse(sensorAnalysis);
    }

    @Transactional
    public void deleteSensorAnalysis(Long id) {
        SensorAnalysisEntity sensorAnalysis = sensorAnalysisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("SensorAnalysis not found with id: " + id));
        sensorAnalysisRepository.delete(sensorAnalysis);
    }
}
