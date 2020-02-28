package com.asys1920.service.controller;

import com.asys1920.dto.UserDTO;
import com.asys1920.dto.UserDTO;
import com.asys1920.mapper.UserMapper;
import com.asys1920.model.User;
import com.asys1920.service.exceptions.UserAlreadyExsitsException;
import com.asys1920.service.exceptions.ValidationException;
import com.asys1920.service.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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


    @ApiOperation(value = "Create a new user", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = PATH)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws ValidationException, UserAlreadyExsitsException {
        validateUserDTO(userDTO);
        User sendUser = UserMapper.INSTANCE.userDTOtoUser(userDTO);
        User createdUser = userService.createUser(sendUser);
        UserDTO responseDTO = UserMapper.INSTANCE.userToUserDTO(createdUser);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get an existing user", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = PATH + "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(
                UserMapper.INSTANCE.userToUserDTO(user),
                HttpStatus.OK);
    }

    @ApiOperation(value = "Updates a specific User", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = PATH + "/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) throws ValidationException {
        validateUserDTO(userDTO);
        User sendUser = UserMapper.INSTANCE.userDTOtoUser(userDTO);
        User createdUser = userService.updateUser(sendUser);
        UserDTO responseDTO = UserMapper.INSTANCE.userToUserDTO(createdUser);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    
    @ApiOperation(value = "Delete an existing user", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted the user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
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
