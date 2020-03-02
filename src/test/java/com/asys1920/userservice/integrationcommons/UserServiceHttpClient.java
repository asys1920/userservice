package com.asys1920.userservice.integrationcommons;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class UserServiceHttpClient {

    private static final String SERVER_URL = "http://localhost";
    private static final String USER_ENDPOINT = "users";

    @LocalServerPort
    private int serverPort;
    private final RestTemplate restTemplate = new RestTemplate();

    private String getUserEndpoint() {
        return SERVER_URL + ":" + serverPort + USER_ENDPOINT;
    }

    public HttpStatus post(final String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        return restTemplate.postForEntity(getUserEndpoint(), entity, String.class).getStatusCode();
    }
}
