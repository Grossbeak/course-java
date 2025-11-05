package com.fooddelivery.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private String status;
    private BigDecimal totalPrice;
    private List<OrderItemDto> items;
    private PaymentDto payment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}



