package com.factory.tycoon.sensordata.service;

import com.factory.tycoon.sensordata.domain.dto.SensorDataRequest;
import com.factory.tycoon.sensordata.domain.dto.SensorDataResponse;
import com.factory.tycoon.sensordata.domain.entity.SensorDataEntity;
import com.factory.tycoon.sensor.domain.entity.SensorEntity;
import com.factory.tycoon.sensordata.repository.SensorDataRepository;
import com.factory.tycoon.sensor.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SensorDataService {

    private final SensorDataRepository sensorDataRepository;
    private final SensorRepository sensorRepository;

    public List<SensorDataResponse> getAllSensorData() {
        return sensorDataRepository.findAll().stream()
                .map(SensorDataResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public SensorDataResponse createSensorData(SensorDataRequest request) {
        SensorEntity sensor = sensorRepository.findById(request.getSensorId())
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found with id: " + request.getSensorId()));

        SensorDataEntity sensorData = SensorDataEntity.builder()
                .sensor(sensor)
                .path(request.getPath())
                .date(request.getDate())
                .build();
        SensorDataEntity saved = sensorDataRepository.save(sensorData);
        return new SensorDataResponse(saved);
    }

    public SensorDataResponse getSensorData(Long id) {
        SensorDataEntity sensorData = sensorDataRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("SensorData not found with id: " + id));
        return new SensorDataResponse(sensorData);
    }

    @Transactional
    public void deleteSensorData(Long id) {
        SensorDataEntity sensorData = sensorDataRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("SensorData not found with id: " + id));
        sensorDataRepository.delete(sensorData);
    }

    public List<SensorDataResponse> getSensorDataBySensorId(Long sensorId, LocalDate date) {
        if (date != null) {
            return sensorDataRepository.findBySensor_SensorIdAndDate(sensorId, date).stream()
                    .map(SensorDataResponse::new)
                    .collect(Collectors.toList());
        }
        return sensorDataRepository.findBySensor_SensorId(sensorId).stream()
                .map(SensorDataResponse::new)
                .collect(Collectors.toList());
    }

    public Object getSensorAnalysis(Long sensorDataId) {
        sensorDataRepository.findById(sensorDataId)
                .orElseThrow(() -> new IllegalArgumentException("SensorData not found with id: " + sensorDataId));
        // TODO: implement sensor analysis logic
        return new Object();
    }
}
