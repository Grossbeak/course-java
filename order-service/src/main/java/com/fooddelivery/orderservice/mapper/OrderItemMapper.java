package com.fooddelivery.orderservice.mapper;

import com.fooddelivery.orderservice.dto.OrderItemDto;
import com.fooddelivery.orderservice.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemDto toDto(OrderItem orderItem);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "id", ignore = true)
    OrderItem toEntity(OrderItemDto orderItemDto);
}

