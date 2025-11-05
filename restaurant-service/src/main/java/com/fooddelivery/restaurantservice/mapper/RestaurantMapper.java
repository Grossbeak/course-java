package com.fooddelivery.restaurantservice.mapper;

import com.fooddelivery.restaurantservice.dto.RestaurantDto;
import com.fooddelivery.restaurantservice.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantDto toDto(Restaurant restaurant);

    @Mapping(target = "dishes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Restaurant toEntity(RestaurantDto restaurantDto);
}

