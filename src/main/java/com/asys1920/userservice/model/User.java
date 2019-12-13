package com.asys1920.userservice.model;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    String emailAddress;
    Date expirationDateDriversLicense;
    @JsonIgnore
    boolean isActive;
    boolean isBanned;

}
