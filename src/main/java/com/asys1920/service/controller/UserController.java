package com.asys1920.service.controller;

import com.asys1920.dto.UserDTO;
import com.asys1920.mapper.UserMapper;
import com.asys1920.model.User;
import com.asys1920.service.exceptions.UserAlreadyExsitsException;
import com.asys1920.service.exceptions.ValidationException;
import com.asys1920.service.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

@Api
@RestController
public class UserController {

    final UserService userService;
    private final String PATH = "/users";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = PATH)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws ValidationException, UserAlreadyExsitsException {
        validateUserDTO(userDTO);
        User sendUser = UserMapper.INSTANCE.userDTOtoUser(userDTO);
        User createdUser = userService.createUser(sendUser);
        UserDTO responseDTO = UserMapper.INSTANCE.userToUserDTO(createdUser);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }




    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = PATH + "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(
                UserMapper.INSTANCE.userToUserDTO(user),
                HttpStatus.OK);
    }

    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = PATH + "/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) throws ValidationException {
        validateUserDTO(userDTO);
        User sendUser = UserMapper.INSTANCE.userDTOtoUser(userDTO);
        User createdUser = userService.updateUser(sendUser);
        UserDTO responseDTO = UserMapper.INSTANCE.userToUserDTO(createdUser);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = PATH + "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void validateUserDTO(UserDTO userDTO) throws ValidationException {
        Set<ConstraintViolation<UserDTO>> validate = Validation.buildDefaultValidatorFactory().getValidator().validate(userDTO);
        if (!validate.isEmpty()) {
            throw new ValidationException(validate);
        }
    }
}
