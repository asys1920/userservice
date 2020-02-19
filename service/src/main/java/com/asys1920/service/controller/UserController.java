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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
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


    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
        User user = userService.getUser(id);

        return new ResponseEntity<>(
                UserMapper.INSTANCE.userToUserDTO(user),
                HttpStatus.OK);
    }

    @PatchMapping("/users/{id}/driverslicenseexpirationdate")
    public ResponseEntity<UserDTO> setDriversLicenseExpirationDate(@PathVariable long id, @RequestBody JSONObject body) throws ValidationException {
        try {
            Date expirationDateDriversLicense = new SimpleDateFormat("yyyy-MM-dd")
                    .parse(body.getAsString("expirationDateDriversLicense"));
            User user = userService.updateDriversLicenseExpirationDate(id, body.getAsString("expirationDateDriversLicense"));
            return new ResponseEntity<>(
                    UserMapper.INSTANCE.userToUserDTO(user),
                    HttpStatus.OK);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new ValidationException("Submitted expirationDateDriversLicense is in the wrong format");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
