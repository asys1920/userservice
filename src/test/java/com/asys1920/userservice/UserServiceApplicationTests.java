package com.asys1920.userservice;

import com.asys1920.userservice.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserServiceApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {
    }

    @Test
    void createUserShouldReturnUserIfUserWasGiven() throws Exception {

    }

    @Test
    void createUserShouldReturnUserIfUserWasGiven2() throws Exception {
        User us = new User();
        us.setUserName("TanteBort");
        us.setFirstName("Bert");
        us.setLastName("Bort");
        us.setEmailAddress("a@b.c");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson = mapper.writeValueAsString(us);

        MvcResult res = mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andExpect(status().isCreated())
				.andReturn();

        String responseBody = res.getResponse().getContentAsString();

        User retrieved = mapper.readValue(responseBody, User.class);
        assertEquals(us,retrieved);
				/*
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(us.getFirstName())));
*/
				System.out.print("");
    }
}
