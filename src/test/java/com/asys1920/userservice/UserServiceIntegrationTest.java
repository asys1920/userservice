package com.asys1920.userservice;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources",
                extraGlue = "com.asys1920.userservice.integrationcommons")
public class UserServiceIntegrationTest {
}
