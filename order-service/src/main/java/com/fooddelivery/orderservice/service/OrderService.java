package com.fooddelivery.orderservice.service;

import com.fooddelivery.orderservice.dto.OrderRequestDto;
import com.fooddelivery.orderservice.dto.OrderResponseDto;
import com.fooddelivery.orderservice.entity.Order;
import com.fooddelivery.orderservice.entity.OrderItem;
import com.fooddelivery.orderservice.entity.Payment;
import com.fooddelivery.orderservice.mapper.OrderItemMapper;
import com.fooddelivery.orderservice.mapper.OrderMapper;
import com.fooddelivery.orderservice.mapper.PaymentMapper;
import com.fooddelivery.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Transactional
    public OrderResponseDto placeOrder(Long userId, OrderRequestDto orderRequest) {
        // TODO: Validate restaurant exists (call restaurant service)
        // TODO: Validate dishes exist and calculate prices (call restaurant service)

        // Calculate total price
        BigDecimal totalPrice = orderRequest.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create order
        Order order = Order.builder()
                .userId(userId)
                .restaurantId(orderRequest.getRestaurantId())
                .status("Placed")
                .totalPrice(totalPrice)
                .build();

        // Create order items
        final Order finalOrder = order;
        List<OrderItem> items = orderRequest.getItems().stream()
                .map(itemDto -> {
                    OrderItem item = orderItemMapper.toEntity(itemDto);
                    item.setOrder(finalOrder);
                    return item;
                })
                .collect(Collectors.toList());
        order.setItems(items);

        // Create payment (mock - always success)
        Payment payment = Payment.builder()
                .method(orderRequest.getPaymentMethod() != null ? orderRequest.getPaymentMethod() : "CreditCard")
                .amount(totalPrice)
                .status("Paid")
                .build();
        payment.setOrder(order);
        order.setPayment(payment);

        order = orderRepository.save(order);

        return orderMapper.toDto(order);
    }

    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toDto(order);
    }

    public List<OrderResponseDto> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<OrderResponseDto> getAllOrders() {
        // TODO: Implement admin check
        return orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponseDto updateOrderStatus(Long id, String status) {
        // TODO: Implement admin check
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }
}

