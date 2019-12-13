package com.asys1920.userservice.adapter;

import com.asys1920.userservice.controller.UserDTO;
import com.asys1920.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    User userDTOtoUser(UserDTO user);

    UserDTO userToUserDTO(User user);
}
