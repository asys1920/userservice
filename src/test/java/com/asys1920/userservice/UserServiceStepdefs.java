package com.asys1920.userservice;

import com.asys1920.userservice.integrationcommons.UserServiceHttpClient;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceStepdefs {

    @Autowired
    UserServiceHttpClient userServiceHttpClient;

    HttpStatus recentHttpStatus;

    @When("^I save a user with:$")
    public void iSaveAUserWith(final String json) {
        recentHttpStatus = userServiceHttpClient.post(json);
    }

    @And("the response is {int}")
    public void theResponseIs(int expectedStatus) {
        assertEquals(recentHttpStatus.value(), expectedStatus);
    }
}
