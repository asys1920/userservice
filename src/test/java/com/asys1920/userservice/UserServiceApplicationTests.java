package com.asys1920.userservice;

import com.asys1920.userservice.model.User;
import com.asys1920.userservice.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void sample() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_CreateUser_When_ValidRequest() throws Exception {
        String body = "{ " +
                "\"firstName\":\"Alexander\"," +
                "\"lastName\":\"Meier\"," +
                "\"emailAddress\":\"a@b.c\"," +
                "\"userName\":\"Fussballgott\"," +
                "\"expirationDateDriversLicense\": \"2022-05-06\"" +
                "}";
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Alexander"))
                .andExpect(jsonPath("$.lastName").value("Meier"))
                .andExpect(jsonPath("$.emailAddress").value("a@b.c"))
                .andExpect(jsonPath("$.userName").value("Fussballgott"))
                .andExpect(jsonPath("$.expirationDateDriversLicense").value("2022-05-06"));
    }

    @Test
    public void should_GetUser_When_ValidRequest() throws Exception {
        User user = new User();
        user.setFirstName("Alexander");
        user.setLastName("Meier");
        user.setUserName("Fussballgott");
        user.setEmailAddress("a@b.c");
        user.setExpirationDateDriversLicense("2022-05-06");
        userRepository.save(user);
        //when(userRepository.findById(1337L).get()).thenReturn(user);

        mockMvc.perform(get("/user/"+user.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alexander"))
                .andExpect(jsonPath("$.lastName").value("Meier"))
                .andExpect(jsonPath("$.emailAddress").value("a@b.c"))
                .andExpect(jsonPath("$.userName").value("Fussballgott"))
                .andExpect(jsonPath("$.expirationDateDriversLicense").value("2022-05-06"));

    }

}