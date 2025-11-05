package com.fooddelivery.orderservice.mapper;

import com.fooddelivery.orderservice.dto.PaymentDto;
import com.fooddelivery.orderservice.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDto toDto(Payment payment);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Payment toEntity(PaymentDto paymentDto);
}

