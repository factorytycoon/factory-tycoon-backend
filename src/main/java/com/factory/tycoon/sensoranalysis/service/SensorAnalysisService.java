package com.factory.tycoon.sensoranalysis.service;

import com.factory.tycoon.sensoranalysis.domain.dto.SensorAnalysisRequest;
import com.factory.tycoon.sensoranalysis.domain.dto.SensorAnalysisResponse;
import com.factory.tycoon.sensoranalysis.domain.entity.SensorAnalysisEntity;
import com.factory.tycoon.sensoranalysis.repository.SensorAnalysisRepository;
import com.factory.tycoon.sensordata.domain.entity.SensorDataEntity;
import com.factory.tycoon.sensordata.repository.SensorDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

        SensorAnalysisEntity sensorAnalysis = SensorAnalysisEntity.builder()
                .sensorData(sensorData)
                .maxValue(request.getMaxValue())
                .minValue(request.getMinValue())
                .avgValue(request.getAvgValue())
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

    public SensorAnalysisResponse getSensorAnalysisBySensorDataId(Long sensorDataId) {
        SensorAnalysisEntity sensorAnalysis = sensorAnalysisRepository.findBySensorData_SensorDataId(sensorDataId)
                .orElseThrow(() -> new IllegalArgumentException("SensorAnalysis not found for sensorDataId: " + sensorDataId));
        return new SensorAnalysisResponse(sensorAnalysis);
    }
}
