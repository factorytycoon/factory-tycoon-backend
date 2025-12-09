package com.factory.tycoon.factory.ctrl;

import com.factory.tycoon.factory.domain.dto.FactoryRequest;
import com.factory.tycoon.factory.domain.dto.FactoryResponse;
import com.factory.tycoon.factory.service.FactoryService;
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
    public ResponseEntity<List<Object>> getEquipments(
            @Parameter(description = "공장 ID", required = true) @PathVariable Long factoryId) {
        return ResponseEntity.ok(factoryService.getEquipments(factoryId));
    }

    @Operation(summary = "공장 재고 목록 조회", 
               description = "특정 공장이 소유한 재고 목록을 조회합니다.")
    @GetMapping("/{factoryId}/inventory")
    public ResponseEntity<List<Object>> getInventory(
            @Parameter(description = "공장 ID", required = true) @PathVariable Long factoryId) {
        return ResponseEntity.ok((List<Object>) (List<?>) factoryService.getInventory(factoryId));
    }
}
