package com.asys1920.userservice.controller;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserDTO {
    Long id;
    String firstName;
    String lastName;
    String userName;
    @NotNull(message = "emailAddress musn't be empty!")
    String emailAddress;
    Date expirationDateDriversLicense;
    boolean isActive;
    boolean isBanned;
}
