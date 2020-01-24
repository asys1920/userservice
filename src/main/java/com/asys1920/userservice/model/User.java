package com.asys1920.userservice.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String firstName;
    String lastName;
    String userName;
    String emailAddress;
    String expirationDateDriversLicense;
    boolean isActive;
    boolean isBanned;

    String street;
    String zipCode;
    String city;
    String country;
}
