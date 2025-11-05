package com.fooddelivery.orderservice.mapper;

import com.fooddelivery.orderservice.dto.OrderResponseDto;
import com.fooddelivery.orderservice.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class, PaymentMapper.class})
public interface OrderMapper {
    @Mapping(target = "items", source = "items")
    @Mapping(target = "payment", source = "payment")
    OrderResponseDto toDto(Order order);
}



