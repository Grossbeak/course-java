package com.fooddelivery.restaurantservice.mapper;

import com.fooddelivery.restaurantservice.dto.DishDto;
import com.fooddelivery.restaurantservice.entity.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DishMapper {
    @Mapping(target = "restaurantId", source = "restaurant.id")
    DishDto toDto(Dish dish);

    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Dish toEntity(DishDto dishDto);
}



