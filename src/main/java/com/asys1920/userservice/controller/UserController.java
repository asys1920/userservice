package com.asys1920.userservice.controller;

import com.asys1920.dto.UserDTO;
import com.asys1920.mapper.UserMapper;
import com.asys1920.model.User;
import com.asys1920.userservice.exceptions.UserAlreadyExistsException;
import com.asys1920.userservice.exceptions.ValidationException;
import com.asys1920.userservice.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
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
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws ValidationException, UserAlreadyExistsException {
        LOG.trace(String.format("GET %s initiated", PATH));
        validateUserDTO(userDTO);
        User sendUser = UserMapper.INSTANCE.userDTOtoUser(userDTO);
        User createdUser = userService.createUser(sendUser);
        UserDTO responseDTO = UserMapper.INSTANCE.userToUserDTO(createdUser);
        LOG.trace(String.format("GET %s completed", PATH));
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
        LOG.trace(String.format("GET %s/%d initiated", PATH, id));
        User user = userService.getUser(id);
        LOG.trace(String.format("GET %s/%d completed", PATH, id));
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
        LOG.trace(String.format("PATCH %s/%d initiated", PATH, userDTO.getId()));
        validateUserDTO(userDTO);
        User sendUser = UserMapper.INSTANCE.userDTOtoUser(userDTO);
        User createdUser = userService.updateUser(sendUser);
        UserDTO responseDTO = UserMapper.INSTANCE.userToUserDTO(createdUser);
        LOG.trace(String.format("PATCH %s/%d completed", PATH, userDTO.getId()));
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
        LOG.trace(String.format("DELETE %s/%d initiated", PATH, id));
        userService.deleteUser(id);
        LOG.trace(String.format("DELETE %s/%d completed", PATH, id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void validateUserDTO(UserDTO userDTO) throws ValidationException {
        Set<ConstraintViolation<UserDTO>> validate = Validation.buildDefaultValidatorFactory().getValidator().validate(userDTO);
        if (!validate.isEmpty()) {
            throw new ValidationException(validate);
        }
    }
}
