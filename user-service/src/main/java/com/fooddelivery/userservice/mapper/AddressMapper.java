package com.fooddelivery.userservice.mapper;

import com.fooddelivery.userservice.dto.AddressDto;
import com.fooddelivery.userservice.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto toDto(Address address);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Address toEntity(AddressDto addressDto);
}



