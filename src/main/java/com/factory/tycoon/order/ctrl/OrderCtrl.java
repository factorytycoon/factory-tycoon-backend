package com.factory.tycoon.order.ctrl;

import com.factory.tycoon.order.domain.dto.OrderRequest;
import com.factory.tycoon.order.domain.dto.OrderResponse;
import com.factory.tycoon.order.service.OrderService;
import com.factory.tycoon.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order", description = "수주 관리 API")
@RestController
@RequestMapping("/api/v1/ft/order")
@RequiredArgsConstructor
public class OrderCtrl {

    private final OrderService orderService;
    private final UserService userService;
    @Operation(summary = "특정 factory의 수주 목록 조회", description = "factory_id에 해당하는 모든 수주 조회")
    @GetMapping("/my-factory")
    public ResponseEntity<List<OrderResponse>> getOrdersByFactoryId(@RequestParam Long factoryId) {
        List<OrderResponse> orders = orderService.getOrdersByFactoryId(factoryId);
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "수주 목록 조회", description = "등록된 수주 목록을 조회합니다. customer 파라미터로 고객명 검색이 가능합니다.")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(
            @Parameter(description = "고객명", required = false) @RequestParam(required = false) String customer) {
        return ResponseEntity.ok(orderService.getAllOrders(customer));
    }

    @Operation(summary = "수주 등록", description = "새로운 수주를 등록합니다.")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @Operation(summary = "수주 상세 조회", description = "특정 수주의 상세 정보를 조회합니다.")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
            @Parameter(description = "수주 ID", required = true) @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @Operation(summary = "수주 수정", description = "기존 수주 정보를 수정합니다.")
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(
            @Parameter(description = "수주 ID", required = true) @PathVariable Long orderId,
            @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, request));
    }

    @Operation(summary = "수주 삭제", description = "특정 수주를 삭제합니다.")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @Parameter(description = "수주 ID", required = true) @PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
