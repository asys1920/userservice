package com.asys1920.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.Validator;

@EntityScan("com.asys1920.*")
@SpringBootApplication
public class UserServiceApplication implements RepositoryRestConfigurer {

    Validator validator;

    public UserServiceApplication(@Qualifier("defaultValidator") Validator validator) {
        this.validator = validator;
    }

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener v) {
        v.addValidator("beforeCreate", validator);
        v.addValidator("beforeSave", validator);
    }
}
