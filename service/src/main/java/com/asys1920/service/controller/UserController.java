package com.asys1920.service.controller;

import com.asys1920.dto.UserDTO;
import com.asys1920.mapper.UserMapper;
import com.asys1920.model.User;
import com.asys1920.service.exceptions.UserAlreadyExsitsException;
import com.asys1920.service.exceptions.ValidationException;
import com.asys1920.service.service.UserService;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@RestController
public class UserController {

    final UserService userService;
    private final String PATH = "/users";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(PATH)
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) throws ValidationException, UserAlreadyExsitsException {
        Set<ConstraintViolation<UserDTO>> validate = Validation.buildDefaultValidatorFactory().getValidator().validate(userDTO);
        if (!validate.isEmpty()) {
            throw new ValidationException(validate);

        }
        User user1 = UserMapper.INSTANCE.userDTOtoUser(userDTO);
        User user = userService.createUser(user1);
        UserDTO responseDTO = UserMapper.INSTANCE.userToUserDTO(user);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    @GetMapping(PATH + "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
        User user = userService.getUser(id);

        return new ResponseEntity<>(
                UserMapper.INSTANCE.userToUserDTO(user),
                HttpStatus.OK);
    }

    @PatchMapping(PATH + "/{id}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) throws ValidationException {
        User user1 = UserMapper.INSTANCE.userDTOtoUser(userDTO);
        User user = userService.updateUser(user1);
        UserDTO responseDTO = UserMapper.INSTANCE.userToUserDTO(user);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping(PATH + "/{id}")
    public ResponseEntity deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
