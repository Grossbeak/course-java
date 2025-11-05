package com.fooddelivery.orderservice.controller;

import com.fooddelivery.orderservice.dto.OrderRequestDto;
import com.fooddelivery.orderservice.dto.OrderResponseDto;
import com.fooddelivery.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order", description = "Order management endpoints")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    @Operation(summary = "Place a new order")
    public ResponseEntity<OrderResponseDto> placeOrder(
            Authentication authentication,
            @Valid @RequestBody OrderRequestDto orderRequest) {
        // TODO: Extract userId from JWT token
        Long userId = 1L; // Placeholder
        OrderResponseDto order = orderService.placeOrder(userId, orderRequest);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    @Operation(summary = "Get orders (USER - own orders, ADMIN - all orders)")
    public ResponseEntity<List<OrderResponseDto>> getOrders(Authentication authentication) {
        // TODO: Check if user is ADMIN, if not return only their orders
        List<OrderResponseDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        OrderResponseDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update order status (Admin only)")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody String status) {
        OrderResponseDto order = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(order);
    }
}



