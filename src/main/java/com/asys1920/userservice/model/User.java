package com.asys1920.userservice.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String firstName;
    String lastName;
    String userName;
    @Pattern(regexp = "^(.+)@(.+)$")
    String emailAddress;
    //@Temporal(TemporalType.DATE)
    String expirationDateDriversLicense;
    boolean isActive;
    boolean isBanned;

    String street;
    String zipCode;
    String city;
    String country;
}
