package com.fooddelivery.userservice.controller;

import com.fooddelivery.userservice.dto.AddressDto;
import com.fooddelivery.userservice.dto.UserDto;
import com.fooddelivery.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "User management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get current user profile")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        UserDto user = userService.getCurrentUser(email);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update current user profile")
    public ResponseEntity<UserDto> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UserDto userDto) {
        String email = authentication.getName();
        UserDto updatedUser = userService.updateProfile(email, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/me/addresses")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Add address to current user")
    public ResponseEntity<AddressDto> addAddress(
            Authentication authentication,
            @Valid @RequestBody AddressDto addressDto) {
        String email = authentication.getName();
        AddressDto address = userService.addAddress(email, addressDto);
        return ResponseEntity.ok(address);
    }
}



