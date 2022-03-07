package com.adi.poc.tests.api;

import com.adi.poc.api.RestApiClient;
import com.adi.poc.api.dto.UserDto;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiTests {
    @Value("${user.bearer}")
    private String bearer;
    @Value("${url.apiBaseUrl}")
    private String apiBaseUrl;
    @Autowired
    RestApiClient restApiClient;

    @Test
    public void createUser() {
        System.out.println("Scenario to validate API user creation is started.");
        String url = apiBaseUrl.concat("/users");
        UserDto userBody = UserDto.builder()
                .email("adi@panda.com")
                .name("Adi Test")
                .gender("mail").build();
        Response response = restApiClient.doPostWithBody(201, url, userBody, bearer);
    }

    @Test
    public void testApi2() {
        System.out.println("Second Api test");
    }
}
