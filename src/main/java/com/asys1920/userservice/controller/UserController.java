package com.asys1920.userservice.controller;

import com.asys1920.userservice.adapter.UserMapper;
import com.asys1920.userservice.exceptions.ValidationException;
import com.asys1920.userservice.model.User;
import com.asys1920.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws ValidationException {
        Set<ConstraintViolation<UserDTO>> validate = Validation.buildDefaultValidatorFactory().getValidator().validate(userDTO);
        if (!validate.isEmpty()) {
            throw new ValidationException(validate);

        }
        User us = UserMapper.INSTANCE.userDTOtoUser(userDTO);
        User user = userService.createUser(us);
        UserDTO responseDTO = UserMapper.INSTANCE.userToUserDTO(user);
        return new ResponseEntity<UserDTO>(responseDTO, HttpStatus.CREATED);
    }


    @GetMapping("/users")
    public ResponseEntity<UserDTO> getAllUser() {
        return null;
    }


}
