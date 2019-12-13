package com.asys1920.userservice.controller;

import com.asys1920.userservice.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URISyntaxException;
import java.util.Date;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserControllerTest {

    @Autowired
    UserController uc;
    MockMvc mvc;

    @Test
    void createUserShouldReturnBadRequestIfNoUserWasGiven() throws URISyntaxException {
        //ResponseEntity res = uc.createUser(null);
        //Assertions.assertEquals(ResponseEntity.badRequest().build(), res);
    }

    @Test
    void createUserShouldReturnReferenceIfUserWasGiven() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        User us = new User();
        us.setUserName("Bert");
        us.setLastName("Bort");
        us.setActive(true);
        us.setUserName("TanteBort");
        us.setExpirationDateDriversLicense(new Date(System.currentTimeMillis()));

        String requestJson=ow.writeValueAsString(us);
/*
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(us.getFirstName())));
*/
    }


}