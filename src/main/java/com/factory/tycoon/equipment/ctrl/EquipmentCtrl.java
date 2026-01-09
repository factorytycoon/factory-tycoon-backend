package com.factory.tycoon.equipment.ctrl;

import com.factory.tycoon.equipment.domain.dto.EquipmentRequest;
import com.factory.tycoon.equipment.domain.dto.EquipmentResponse;
import com.factory.tycoon.equipment.service.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Equipment", description = "설비 관리 API")
@RestController
@RequestMapping("/api/v1/ft/equipment")
@RequiredArgsConstructor
public class EquipmentCtrl {
    @Operation(summary = "공장별 설비 목록 조회", description = "factoryId로 해당 공장에 속한 설비 목록 조회")
    @GetMapping(params = "factoryId")
    public ResponseEntity<List<EquipmentResponse>> getEquipmentsByFactoryId(@RequestParam Long factoryId) {
        return ResponseEntity.ok(equipmentService.getEquipmentsByFactoryId(factoryId));
    }

    private final EquipmentService equipmentService;

    @Operation(summary = "설비 목록 조회", 
               description = "등록된 모든 설비 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<EquipmentResponse>> getEquipments() {
        return ResponseEntity.ok(equipmentService.getEquipments());
    }

    @Operation(summary = "설비 생성", 
               description = "새로운 설비를 생성합니다.")
    @PostMapping
    public ResponseEntity<EquipmentResponse> createEquipment(@RequestBody EquipmentRequest request) {
        return ResponseEntity.ok(equipmentService.createEquipment(request));
    }

    @Operation(summary = "설비 단건 조회", 
               description = "특정 설비의 상세 정보를 조회합니다.")
    @GetMapping("/{equipmentId}")
    public ResponseEntity<EquipmentResponse> getEquipment(
            @Parameter(description = "설비 ID", required = true) @PathVariable Long equipmentId) {
        return ResponseEntity.ok(equipmentService.getEquipment(equipmentId));
    }

    @Operation(summary = "설비 정보 수정", 
               description = "기존 설비의 정보를 수정합니다.")
    @PutMapping("/{equipmentId}")
    public ResponseEntity<EquipmentResponse> updateEquipment(
            @Parameter(description = "설비 ID", required = true) @PathVariable Long equipmentId,
            @RequestBody EquipmentRequest request) {
        return ResponseEntity.ok(equipmentService.updateEquipment(equipmentId, request));
    }

    @Operation(summary = "설비 삭제", 
               description = "특정 설비를 삭제합니다.")
    @DeleteMapping("/{equipmentId}")
    public ResponseEntity<Void> deleteEquipment(
            @Parameter(description = "설비 ID", required = true) @PathVariable Long equipmentId) {
        equipmentService.deleteEquipment(equipmentId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "설비 센서 목록 조회", 
               description = "특정 설비에 속한 센서 목록을 조회합니다.")
    @GetMapping("/{equipmentId}/sensors")
    public ResponseEntity<List<Object>> getSensors(
            @Parameter(description = "설비 ID", required = true) @PathVariable Long equipmentId) {
        return ResponseEntity.ok(equipmentService.getSensors(equipmentId));
    }

    @Operation(summary = "설비 알람 목록 조회", 
               description = "특정 설비에 속한 알람 목록을 조회합니다.")
    @GetMapping("/{equipmentId}/alarm")
    public ResponseEntity<List<Object>> getAlarms(
            @Parameter(description = "설비 ID", required = true) @PathVariable Long equipmentId) {
        return ResponseEntity.ok(equipmentService.getAlarms(equipmentId));
    }
}
