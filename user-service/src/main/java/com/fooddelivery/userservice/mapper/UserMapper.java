package com.fooddelivery.userservice.mapper;

import com.fooddelivery.userservice.dto.AddressDto;
import com.fooddelivery.userservice.dto.UserDto;
import com.fooddelivery.userservice.entity.Address;
import com.fooddelivery.userservice.entity.Role;
import com.fooddelivery.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface UserMapper {
    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToStrings")
    @Mapping(target = "addresses", source = "addresses")
    UserDto toDto(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User toEntity(UserDto userDto);

    @Named("rolesToStrings")
    default List<String> rolesToStrings(List<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}



