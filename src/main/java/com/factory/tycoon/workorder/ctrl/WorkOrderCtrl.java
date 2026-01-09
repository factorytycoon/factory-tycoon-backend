package com.factory.tycoon.workorder.ctrl;

import com.factory.tycoon.workorder.domain.dto.WorkOrderRequest;
import com.factory.tycoon.workorder.domain.dto.WorkOrderResponse;
import com.factory.tycoon.workorder.service.WorkOrderService;
import com.factory.tycoon.schedule.domain.dto.ScheduleResponse;
import com.factory.tycoon.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "WorkOrder", description = "작업지시서 관리 API")
@RestController
@RequestMapping("/api/v1/ft/work-order")
@RequiredArgsConstructor
public class WorkOrderCtrl {

    @Operation(summary = "공장별 Workorder 목록 조회", description = "특정 factory에 해당하는 모든 Workorder 조회")
    @GetMapping("/factory/{factoryId}")
    public ResponseEntity<List<WorkOrderResponse>> getWorkOrdersByFactoryId(
            @Parameter(description = "공장 ID", required = true) @PathVariable Long factoryId) {
        return ResponseEntity.ok(workorderService.getWorkOrdersByFactoryId(factoryId));
    }

    private final WorkOrderService workorderService;
    private final ScheduleService scheduleService;

    @Operation(summary = "작업지시서 목록 조회", 
               description = "모든 작업지시서 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<WorkOrderResponse>> getWorkOrders() {
        return ResponseEntity.ok(workorderService.getWorkOrders());
    }

    @Operation(summary = "작업지시서 생성", 
               description = "새로운 작업지시서를 생성합니다.")
    @PostMapping
    public ResponseEntity<WorkOrderResponse> createWorkOrder(@RequestBody WorkOrderRequest request) {
        return ResponseEntity.ok(workorderService.createWorkOrder(request));
    }

    @Operation(summary = "작업지시서 단건 조회", 
               description = "특정 작업지시서의 상세 정보를 조회합니다.")
    @GetMapping("/{workorderId}")
    public ResponseEntity<WorkOrderResponse> getWorkOrder(
            @Parameter(description = "작업지시서 ID", required = true) @PathVariable Long workorderId) {
        return ResponseEntity.ok(workorderService.getWorkOrder(workorderId));
    }

    @Operation(summary = "작업지시서 수정", 
               description = "기존 작업지시서의 정보를 수정합니다.")
    @PutMapping("/{workorderId}")
    public ResponseEntity<WorkOrderResponse> updateWorkOrder(
            @Parameter(description = "작업지시서 ID", required = true) @PathVariable Long workorderId,
            @RequestBody WorkOrderRequest request) {
        return ResponseEntity.ok(workorderService.updateWorkOrder(workorderId, request));
    }

    @Operation(summary = "작업지시서 삭제", 
               description = "특정 작업지시서를 삭제합니다.")
    @DeleteMapping("/{workorderId}")
    public ResponseEntity<Void> deleteWorkOrder(
            @Parameter(description = "작업지시서 ID", required = true) @PathVariable Long workorderId) {
        workorderService.deleteWorkOrder(workorderId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "작업지시서의 스케줄 목록 조회", 
               description = "특정 작업지시서에 속한 스케줄 목록을 조회합니다.")
    @GetMapping("/{workorderId}/schedules")
    public ResponseEntity<List<ScheduleResponse>> getSchedulesByWorkorderId(
            @Parameter(description = "작업지시서 ID", required = true) @PathVariable Long workorderId) {
        return ResponseEntity.ok(scheduleService.getSchedulesByWorkorderId(workorderId));
    }
}
