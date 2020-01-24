package com.asys1920.userservice;

import com.asys1920.userservice.model.User;
import com.asys1920.userservice.repository.UserRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.SimpleDateFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    String userEndpoint = "/users";

    @Test
    public void should_ReturnValid_When_Get_ValidRequest() throws Exception {
        mockMvc.perform(get(userEndpoint))
                .andExpect(status().isOk());
    }

    @Test
    public void should_ReturnErrorMessage_When_Post_InvalidDate() throws Exception {
        JSONObject body = new JSONObject();
        body.put("firstName", "Alexander");
        body.put("lastName", "Meier");
        body.put("userName", "Fussballgott");
        body.put("emailAddress", "a@b.c");
        body.put("expirationDateDriversLicense", "2022-05-A");
        mockMvc.perform(post(userEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void should_ReturnErrorMessage_When_Post_LicenseIsToOld() throws Exception {
        JSONObject body = new JSONObject();
        body.put("firstName", "Alexander");
        body.put("lastName", "Meier");
        body.put("userName", "Fussballgott");
        body.put("emailAddress", "a@b.c");
        body.put("expirationDateDriversLicense", "2005-05-01");
        mockMvc.perform(post(userEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_ReturnErrorMessage_When_Post_ExistingUser() throws Exception {
        JSONObject body = new JSONObject();
        body.put("firstName", "Alexander");
        body.put("lastName", "Meier");
        body.put("userName", "Fussballgott");
        body.put("emailAddress", "a@b.c");
        body.put("expirationDateDriversLicense", "2022-05-01");

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
        String link = response.getJSONObject("_links").getString("user");
        String[] splitLink = link.split("/");
        String id = splitLink[splitLink.length - 1].split("\"")[0];
        //Post to the id from the user just created
        mockMvc.perform(post(userEndpoint+"/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void should_CreateUser_When_Post_ValidRequest() throws Exception {
        JSONObject body = new JSONObject();
        body.put("firstName", "Alexander");
        body.put("lastName", "Meier");
        body.put("userName", "Fussballgott");
        body.put("emailAddress", "a@b.c");
        body.put("expirationDateDriversLicense", "2022-05-05");
        mockMvc.perform(post(userEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(body.get("firstName")))
                .andExpect(jsonPath("$.lastName").value(body.get("lastName")))
                .andExpect(jsonPath("$.emailAddress").value(body.get("emailAddress")))
                .andExpect(jsonPath("$.userName").value(body.get("userName")))
                .andExpect(jsonPath("$.expirationDateDriversLicense").value(body.get("expirationDateDriversLicense")));
    }

    @Test
    public void should_GetUser_When_Post_ValidRequest() throws Exception {
        User user = new User();
        user.setFirstName("Alexander");
        user.setLastName("Meier");
        user.setUserName("Fussballgott");
        user.setEmailAddress("a@b.c");
        //user.setExpirationDateDriversLicense(new SimpleDateFormat("yyyy-MM-dd")
        //        .parse("2022-05-06"));
        user.setExpirationDateDriversLicense("2022-05-06");
        userRepository.save(user);

        mockMvc.perform(get(userEndpoint+"/" + user.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alexander"))
                .andExpect(jsonPath("$.lastName").value("Meier"))
                .andExpect(jsonPath("$.emailAddress").value("a@b.c"))
                .andExpect(jsonPath("$.userName").value("Fussballgott"))
                .andExpect(jsonPath("$.expirationDateDriversLicense").value("2022-05-06"));

    }

}