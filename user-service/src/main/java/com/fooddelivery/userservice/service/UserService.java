package com.fooddelivery.userservice.service;

import com.fooddelivery.userservice.dto.AddressDto;
import com.fooddelivery.userservice.dto.AuthResponseDto;
import com.fooddelivery.userservice.dto.LoginRequestDto;
import com.fooddelivery.userservice.dto.RegisterRequestDto;
import com.fooddelivery.userservice.dto.UserDto;
import com.fooddelivery.userservice.entity.Address;
import com.fooddelivery.userservice.entity.Role;
import com.fooddelivery.userservice.entity.User;
import com.fooddelivery.userservice.mapper.AddressMapper;
import com.fooddelivery.userservice.mapper.UserMapper;
import com.fooddelivery.userservice.repository.AddressRepository;
import com.fooddelivery.userservice.repository.RoleRepository;
import com.fooddelivery.userservice.repository.UserRepository;
import com.fooddelivery.userservice.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Transactional
    public AuthResponseDto register(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));
        user.setRoles(Collections.singletonList(userRole));

        user = userRepository.save(user);

        String token = jwtTokenProvider.generateToken(user.getEmail());
        UserDto userDto = userMapper.toDto(user);

        return AuthResponseDto.builder()
                .token(token)
                .user(userDto)
                .build();
    }

    public AuthResponseDto login(LoginRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtTokenProvider.generateToken(user.getEmail());
        UserDto userDto = userMapper.toDto(user);

        return AuthResponseDto.builder()
                .token(token)
                .user(userDto)
                .build();
    }

    public UserDto getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto updateProfile(String email, UserDto userDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userDto.getName());
        user = userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Transactional
    public AddressDto addAddress(String email, AddressDto addressDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressMapper.toEntity(addressDto);
        address.setUser(user);
        address = addressRepository.save(address);

        return addressMapper.toDto(address);
    }
}



