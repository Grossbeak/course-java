package com.fooddelivery.restaurantservice.service;

import com.fooddelivery.restaurantservice.dto.DishDto;
import com.fooddelivery.restaurantservice.dto.RestaurantDto;
import com.fooddelivery.restaurantservice.entity.Dish;
import com.fooddelivery.restaurantservice.entity.Restaurant;
import com.fooddelivery.restaurantservice.mapper.DishMapper;
import com.fooddelivery.restaurantservice.mapper.RestaurantMapper;
import com.fooddelivery.restaurantservice.repository.DishRepository;
import com.fooddelivery.restaurantservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private DishMapper dishMapper;

    public List<RestaurantDto> getAllRestaurants() {
        // TODO: Implement filtering logic
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toDto)
                .collect(Collectors.toList());
    }

    public RestaurantDto getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        return restaurantMapper.toDto(restaurant);
    }

    public List<DishDto> getDishesByRestaurantId(Long restaurantId) {
        return dishRepository.findByRestaurantId(restaurantId).stream()
                .map(dishMapper::toDto)
                .collect(Collectors.toList());
    }

    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        // TODO: Implement admin check
        Restaurant restaurant = restaurantMapper.toEntity(restaurantDto);
        restaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDto(restaurant);
    }

    public RestaurantDto updateRestaurant(Long id, RestaurantDto restaurantDto) {
        // TODO: Implement admin check
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        restaurant.setName(restaurantDto.getName());
        restaurant.setCuisineType(restaurantDto.getCuisineType());
        restaurant.setLocation(restaurantDto.getLocation());
        restaurant.setAddress(restaurantDto.getAddress());
        restaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDto(restaurant);
    }

    public void deleteRestaurant(Long id) {
        // TODO: Implement admin check
        restaurantRepository.deleteById(id);
    }

    public DishDto createDish(Long restaurantId, DishDto dishDto) {
        // TODO: Implement admin check
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        Dish dish = dishMapper.toEntity(dishDto);
        dish.setRestaurant(restaurant);
        dish = dishRepository.save(dish);
        return dishMapper.toDto(dish);
    }

    public DishDto updateDish(Long restaurantId, Long dishId, DishDto dishDto) {
        // TODO: Implement admin check
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found"));
        dish.setName(dishDto.getName());
        dish.setDescription(dishDto.getDescription());
        dish.setPrice(dishDto.getPrice());
        dish.setImageUrl(dishDto.getImageUrl());
        dish = dishRepository.save(dish);
        return dishMapper.toDto(dish);
    }

    public void deleteDish(Long restaurantId, Long dishId) {
        // TODO: Implement admin check
        dishRepository.deleteById(dishId);
    }
}



