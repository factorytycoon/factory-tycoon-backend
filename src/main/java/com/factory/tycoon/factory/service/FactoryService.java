package com.factory.tycoon.factory.service;

import com.factory.tycoon.factory.domain.Factory;
import com.factory.tycoon.factory.dto.FactoryRequest;
import com.factory.tycoon.factory.dto.FactoryResponse;
import com.factory.tycoon.factory.repository.FactoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FactoryService {

    private final FactoryRepository factoryRepository;

    public List<FactoryResponse> getFactories() {
        return factoryRepository.findAll().stream()
                .map(FactoryResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public FactoryResponse createFactory(FactoryRequest request) {
        Factory factory = Factory.builder()
                .name(request.getName())
                .location(request.getLocation())
                .description(request.getDescription())
                .phone(request.getPhone())
                .build();
        Factory savedFactory = factoryRepository.save(factory);
        return new FactoryResponse(savedFactory);
    }

    public FactoryResponse getFactory(Long id) {
        Factory factory = factoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Factory not found with id: " + id));
        return new FactoryResponse(factory);
    }

    @Transactional
    public FactoryResponse updateFactory(Long id, FactoryRequest request) {
        Factory factory = factoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Factory not found with id: " + id));
        factory.update(request.getName(), request.getLocation(), request.getDescription(), request.getPhone());
        return new FactoryResponse(factory);
    }

    @Transactional
    public void deleteFactory(Long id) {
        Factory factory = factoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Factory not found with id: " + id));
        factoryRepository.delete(factory);
    }

    public List<Object> getEquipments(Long factoryId) {
        // TODO: Implement equipment logic
        return Collections.emptyList();
    }
}
