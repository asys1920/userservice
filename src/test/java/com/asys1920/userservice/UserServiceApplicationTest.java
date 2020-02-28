package com.asys1920.userservice;

import com.asys1920.model.User;
import com.asys1920.userservice.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    String userEndpoint = "/users";


    @Test
    void contextLoads() {
    }

    /* Test GET /users/{id} */
    @Test
    public void should_ReturnValidUser_When_RequestingValidId() throws Exception {
        User user = createdUser();
        mockMvc.perform(get(userEndpoint + "/" + user.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.emailAddress").value(user.getEmailAddress()))
                .andExpect(jsonPath("$.userName").value(user.getUserName()))
                .andExpect(jsonPath("$.expirationDateDriversLicense").value(user.getExpirationDateDriversLicense().toString()));
    }

    @Test
    void should_ReturnErrorMessage_When_RequestingInvalidUser() throws Exception {
        mockMvc.perform(get(userEndpoint + "/" + getRandomId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void should_ReturnErrorMessage_When_CreatingInvalidUser_InvalidEmail() throws Exception {
        JSONObject body = getValidUser();
        body.put("emailAddress", "aaaaaaaaaaaaaaaaab#@bbbbaa");
        mockMvc.perform(post(userEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_ReturnErrorMessage_When_CreatingInvalidUser_OldDriversLicense() throws Exception {
        JSONObject body = getValidUser();
        body.put("expirationDateDriversLicense", "2005-05-01");
        mockMvc.perform(post(userEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_ReturnErrorMessage_When_CreatingUserWithTakenId() throws Exception {
        JSONObject body = getValidUser();
        //Create a user
        MvcResult result = mockMvc.perform(post(userEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(body.get("firstName")))
                .andExpect(jsonPath("$.lastName").value(body.get("lastName")))
                .andExpect(jsonPath("$.emailAddress").value(body.get("emailAddress")))
                .andExpect(jsonPath("$.userName").value(body.get("userName")))
                .andExpect(jsonPath("$.expirationDateDriversLicense").value(body.get("expirationDateDriversLicense")))
                .andReturn();
        JSONObject response = new JSONObject(result.getResponse().getContentAsString());
        String id = response.getString("id");
        body.put("id", id);
        body.put("firstName", body.getString("firstName") + "asdf");
        //Post to the id from the user just created
        mockMvc.perform(post(userEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    /* Test PATCH /users */
    @Test
    public void should_ReturnValidUser_When_UpdatingValidUser() throws Exception {
        JSONObject body = getValidUser();
        body.put("firstName", "Gerd");
        mockMvc.perform(patch(userEndpoint + "/" + getRandomId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(body.get("firstName")))
                .andExpect(jsonPath("$.lastName").value(body.get("lastName")))
                .andExpect(jsonPath("$.emailAddress").value(body.get("emailAddress")))
                .andExpect(jsonPath("$.userName").value(body.get("userName")))
                .andExpect(jsonPath("$.expirationDateDriversLicense").value(body.get("expirationDateDriversLicense")))
                .andReturn();
    }

    @Test
    public void should_ReturnErrorMessage_When_UpdatingInvalidUser_InvalidEmail() throws Exception {
        JSONObject body = getValidUser();
        body.put("emailAddress", "aaaaaaaaaabbbbbbbbbbbbbb@ccccccccc");
        mockMvc.perform(patch(userEndpoint + "/" + getRandomId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_ReturnErrorMessage_When_UpdatingInvalidUser_OldDriversLicense() throws Exception {
        JSONObject body = getValidUser();
        body.put("expirationDateDriversLicense", "2005-05-01");
        mockMvc.perform(patch(userEndpoint + "/" + getRandomId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_ReturnErrorMessage_When_UpdatingInvalidUser_InvalidDriversLicense() throws Exception {
        User user = createdUser();
        JSONObject body = getValidUser();
        body.put("expirationDateDriversLicense", "2022-05-A");
        mockMvc.perform(patch(userEndpoint + "/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /* Test Delete /users/{id} */
    @Test
    public void should_ReturnOk_When_DeletingValidId() throws Exception {
        User user = createdUser();
        mockMvc.perform(delete(userEndpoint + "/" + user.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_ReturnErrorMessage_When_DeletingInvalidId() throws Exception {
        mockMvc.perform(delete(userEndpoint + "/" + +getRandomId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    private JSONObject getValidUser() throws JSONException {
        JSONObject body = new JSONObject();
        body.put("firstName", "Alexander");
        body.put("lastName", "Meier");
        body.put("userName", "Fussballgott");
        body.put("emailAddress", "a@b.c");
        body.put("expirationDateDriversLicense", Instant.now().toString());
        body.put("street", "Mörfelder Landstraße 362");
        body.put("zipCode", "60528");
        body.put("city", "Frankfurt am Main");
        body.put("country", "Germany");
        return body;
    }

    private int getRandomId() {
        return (int) (Math.random() * Integer.MAX_VALUE);
    }


    private User createdUser() throws JSONException {
        User user = new User();
        JSONObject validUser = getValidUser();
        user.setFirstName(validUser.getString("firstName"));
        user.setLastName(validUser.getString("lastName"));
        user.setUserName(validUser.getString("userName"));
        user.setEmailAddress(validUser.getString("emailAddress"));
        user.setExpirationDateDriversLicense(Instant.now());
        userRepository.save(user);
        return user;
    }
}

