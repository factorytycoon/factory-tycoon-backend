package com.factory.tycoon.factory.ctrl;

import com.factory.tycoon.factory.dto.FactoryRequest;
import com.factory.tycoon.factory.dto.FactoryResponse;
import com.factory.tycoon.factory.service.FactoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ft/factory")
@RequiredArgsConstructor
public class FactoryCtrl {

    private final FactoryService factoryService;

    @GetMapping
    public ResponseEntity<List<FactoryResponse>> getFactories() {
        return ResponseEntity.ok(factoryService.getFactories());
    }

    @PostMapping
    public ResponseEntity<FactoryResponse> createFactory(@RequestBody FactoryRequest request) {
        return ResponseEntity.ok(factoryService.createFactory(request));
    }

    @GetMapping("/{factoryId}")
    public ResponseEntity<FactoryResponse> getFactory(@PathVariable Long factoryId) {
        return ResponseEntity.ok(factoryService.getFactory(factoryId));
    }

    @PutMapping("/{factoryId}")
    public ResponseEntity<FactoryResponse> updateFactory(@PathVariable Long factoryId, @RequestBody FactoryRequest request) {
        return ResponseEntity.ok(factoryService.updateFactory(factoryId, request));
    }

    @DeleteMapping("/{factoryId}")
    public ResponseEntity<Void> deleteFactory(@PathVariable Long factoryId) {
        factoryService.deleteFactory(factoryId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{factoryId}/equipments")
    public ResponseEntity<List<Object>> getEquipments(@PathVariable Long factoryId) {
        return ResponseEntity.ok(factoryService.getEquipments(factoryId));
    }
}
