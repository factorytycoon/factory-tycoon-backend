package com.factory.tycoon.inventory.ctrl;

import com.factory.tycoon.inventory.domain.dto.InventoryRequest;
import com.factory.tycoon.inventory.domain.dto.InventoryResponse;
import com.factory.tycoon.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Inventory", description = "재고 관리 API")
@RestController
@RequestMapping("/api/v1/ft/inventory")
@RequiredArgsConstructor
public class InventoryCtrl {

    private final InventoryService inventoryService;

    @Operation(summary = "재고 목록 조회", description = "등록된 모든 재고 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @Operation(summary = "재고 등록", description = "새로운 재고를 등록합니다.")
    @PostMapping
    public ResponseEntity<InventoryResponse> createInventory(@RequestBody InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.createInventory(request));
    }

    @Operation(summary = "재고 상세 조회", description = "특정 재고의 상세 정보를 조회합니다.")
    @GetMapping("/{inventoryId}")
    public ResponseEntity<InventoryResponse> getInventory(
            @Parameter(description = "재고 ID", required = true) @PathVariable Long inventoryId) {
        return ResponseEntity.ok(inventoryService.getInventory(inventoryId));
    }

    @Operation(summary = "재고 수정", description = "기존 재고 정보를 수정합니다.")
    @PutMapping("/{inventoryId}")
    public ResponseEntity<InventoryResponse> updateInventory(
            @Parameter(description = "재고 ID", required = true) @PathVariable Long inventoryId,
            @RequestBody InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.updateInventory(inventoryId, request));
    }

    @Operation(summary = "재고 삭제", description = "특정 재고를 삭제합니다.")
    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<Void> deleteInventory(
            @Parameter(description = "재고 ID", required = true) @PathVariable Long inventoryId) {
        inventoryService.deleteInventory(inventoryId);
        return ResponseEntity.ok().build();
    }
}
