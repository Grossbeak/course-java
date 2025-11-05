package com.fooddelivery.restaurantservice.controller;

import com.fooddelivery.restaurantservice.dto.DishDto;
import com.fooddelivery.restaurantservice.dto.RestaurantDto;
import com.fooddelivery.restaurantservice.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@Tag(name = "Restaurant", description = "Restaurant management endpoints")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    @Operation(summary = "Get all restaurants")
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        List<RestaurantDto> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get restaurant by ID")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable Long id) {
        RestaurantDto restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/{id}/dishes")
    @Operation(summary = "Get dishes by restaurant ID")
    public ResponseEntity<List<DishDto>> getDishesByRestaurantId(@PathVariable Long id) {
        List<DishDto> dishes = restaurantService.getDishesByRestaurantId(id);
        return ResponseEntity.ok(dishes);
    }

    @PostMapping
    @Operation(summary = "Create restaurant (Admin only)")
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurantDto) {
        RestaurantDto created = restaurantService.createRestaurant(restaurantDto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update restaurant (Admin only)")
    public ResponseEntity<RestaurantDto> updateRestaurant(
            @PathVariable Long id,
            @RequestBody RestaurantDto restaurantDto) {
        RestaurantDto updated = restaurantService.updateRestaurant(id, restaurantDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete restaurant (Admin only)")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/dishes")
    @Operation(summary = "Create dish (Admin only)")
    public ResponseEntity<DishDto> createDish(
            @PathVariable Long id,
            @RequestBody DishDto dishDto) {
        DishDto created = restaurantService.createDish(id, dishDto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}/dishes/{dishId}")
    @Operation(summary = "Update dish (Admin only)")
    public ResponseEntity<DishDto> updateDish(
            @PathVariable Long id,
            @PathVariable Long dishId,
            @RequestBody DishDto dishDto) {
        DishDto updated = restaurantService.updateDish(id, dishId, dishDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}/dishes/{dishId}")
    @Operation(summary = "Delete dish (Admin only)")
    public ResponseEntity<Void> deleteDish(
            @PathVariable Long id,
            @PathVariable Long dishId) {
        restaurantService.deleteDish(id, dishId);
        return ResponseEntity.noContent().build();
    }
}



