package com.factory.tycoon.factory.ctrl;

import com.factory.tycoon.factory.domain.dto.FactoryRequest;
import com.factory.tycoon.factory.domain.dto.FactoryResponse;
import com.factory.tycoon.factory.service.FactoryService;
import com.factory.tycoon.equipment.domain.dto.EquipmentResponse;
import com.factory.tycoon.inventory.domain.dto.InventoryResponse;
import com.factory.tycoon.order.domain.dto.OrderResponse;
import com.factory.tycoon.prediction.domain.dto.PredictionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Factory", description = "공장 관리 API")
@RestController
@RequestMapping("/api/v1/ft/factory")
@RequiredArgsConstructor
public class FactoryCtrl {

    private final FactoryService factoryService;

    @Operation(summary = "공장 목록 조회", 
               description = "등록된 모든 공장 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<FactoryResponse>> getFactories() {
        return ResponseEntity.ok(factoryService.getFactories());
    }

    @Operation(summary = "공장 생성", 
               description = "새로운 공장을 생성합니다.")
    @PostMapping
    public ResponseEntity<FactoryResponse> createFactory(@RequestBody FactoryRequest request) {
        return ResponseEntity.ok(factoryService.createFactory(request));
    }

    @Operation(summary = "공장 단건 조회", 
               description = "특정 공장의 상세 정보를 조회합니다.")
    @GetMapping("/{factoryId}")
    public ResponseEntity<FactoryResponse> getFactory(
            @Parameter(description = "공장 ID", required = true) @PathVariable Long factoryId) {
        return ResponseEntity.ok(factoryService.getFactory(factoryId));
    }

    @Operation(summary = "공장 정보 수정", 
               description = "기존 공장의 정보를 수정합니다.")
    @PutMapping("/{factoryId}")
    public ResponseEntity<FactoryResponse> updateFactory(
            @Parameter(description = "공장 ID", required = true) @PathVariable Long factoryId,
            @RequestBody FactoryRequest request) {
        return ResponseEntity.ok(factoryService.updateFactory(factoryId, request));
    }

    @Operation(summary = "공장 삭제", 
               description = "특정 공장을 삭제합니다.")
    @DeleteMapping("/{factoryId}")
    public ResponseEntity<Void> deleteFactory(
            @Parameter(description = "공장 ID", required = true) @PathVariable Long factoryId) {
        factoryService.deleteFactory(factoryId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "공장 장비 목록 조회", 
               description = "특정 공장에 속한 장비 목록을 조회합니다.")
    @GetMapping("/{factoryId}/equipments")
    public ResponseEntity<List<EquipmentResponse>> getEquipments(
            @Parameter(description = "공장 ID", required = true) @PathVariable Long factoryId) {
        return ResponseEntity.ok(factoryService.getEquipments(factoryId));
    }

    @Operation(summary = "공장 재고 목록 조회", 
               description = "특정 공장이 소유한 재고 목록을 조회합니다.")
    @GetMapping("/{factoryId}/inventory")
    public ResponseEntity<List<InventoryResponse>> getInventory(
            @Parameter(description = "공장 ID", required = true) @PathVariable Long factoryId) {
        return ResponseEntity.ok(factoryService.getInventory(factoryId));
    }

    @Operation(summary = "공장 수주 목록 조회", 
               description = "특정 공장이 받은 수주 목록을 조회합니다.")
    @GetMapping("/{factoryId}/orders")
    public ResponseEntity<List<OrderResponse>> getOrders(
            @Parameter(description = "공장 ID", required = true) @PathVariable Long factoryId) {
        return ResponseEntity.ok(factoryService.getOrders(factoryId));
    }

    @Operation(summary = "공장 예측값 조회",
               description = "특정 공장의 예측값을 타입/레벨/선택여부로 필터링하여 조회합니다.")
    @GetMapping("/{factoryId}/predictions")
    public ResponseEntity<List<PredictionResponse>> getPredictionsByFactory(
            @Parameter(description = "공장 ID", required = true) @PathVariable Long factoryId,
            @Parameter(description = "예측 타입") @RequestParam(required = false) String type,
            @Parameter(description = "예측 레벨 (warning/critical)") @RequestParam(required = false) String level,
            @Parameter(description = "선택 상태 (true: 선택됨, false: 미선택)") @RequestParam(required = false) Boolean selected) {
        return ResponseEntity.ok(factoryService.getPredictions(factoryId, type, level, selected));
    }

    @Operation(summary = "공장 모델링 정보 수정",
               description = "URL 인코딩된 모델링 경로/JSON 문자열을 PathVariable로 받아 공장 DB에 반영합니다.")
    @PutMapping("/{factoryId}/modeling/{modeling:.+}")
    public ResponseEntity<FactoryResponse> updateFactoryModeling(
            @Parameter(description = "공장 ID", required = true) @PathVariable Long factoryId,
            @Parameter(description = "URL 인코딩된 모델링 문자열", required = true) @PathVariable String modeling) {
        return ResponseEntity.ok(factoryService.updateFactoryModeling(factoryId, modeling));
    }
}
