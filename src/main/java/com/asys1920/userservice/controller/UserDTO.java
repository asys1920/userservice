package com.asys1920.userservice.controller;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserDTO {
    Long id;
    String firstName;
    String lastName;
    String userName;
    String emailAddress;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    String expirationDateDriversLicense;
    boolean isActive;
    boolean isBanned;

    String street;
    String zipCode;
    String city;
    String country;
}
