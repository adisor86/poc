package com.adi.poc.tests.api;

import com.adi.poc.api.RestApiClient;
import com.adi.poc.api.dto.User;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiTests {
    @Value("${user.bearer}")
    private String bearer;
    @Value("${url.apiUrl}")
    private String apiUrl;
    @Autowired
    RestApiClient restApiClient;
    private String userId;
    private Map<String, String> pathParams;
    private final String name = "Adi Test";
    private final String email = "aditest@bitpanda.com";
    private final String gender = "male";
    private final String status = "active";
    private User user;

    @Test
    @Order(1)
    public void createUser() {
        System.out.println("Scenario to validate API user creation is started.");
        User userBody = User.builder()
                .email(email)
                .name(name)
                .gender(gender)
                .status(status).build();
        Response response = restApiClient.doPostRequestWithBody(201, apiUrl, userBody, bearer);
        this.userId = response.jsonPath().get("id").toString();
    }

    @Test
    @Order(1)
    public void createUserWithInvalidBody() {
        System.out.println("Scenario to validate API user creation with invalid body.");
        User userBody = User.builder()
                .email(email)
                .name(name)
                .gender("gender")
                .status(status).build();
        //Note test will fail, due to the wrong API implementation; when invalid body is provided we should receive 400 and not 422
        restApiClient.doPostRequestWithBody(400, apiUrl, userBody, bearer);
    }

    @Test
    @Order(1)
    public void createUserWithEmptyBody() {
        System.out.println("Scenario to validate API user creation with invalid body.");
        String userBody = "";
        String expectedResult = "[{field=email, message=can't be blank}, {field=name, message=can't be blank}, {field=gender, message=can't be blank}, {field=status, message=can't be blank}]";
        //Note: normally test should fail due to 422 status code, but in the assertion for status code I passed it on purpose just to validate messages from response
        Response response = restApiClient.doPostRequestWithBody(422, apiUrl, userBody, bearer);
        Assertions.assertEquals(expectedResult, response.jsonPath().get().toString());
    }

    @Test
    @Order(1)
    public void createUserWhileUsingWrongEndpointWhichDoesNotSupportPostMethod() {
        System.out.println("Scenario to validate that POST operation cannot be performed on an endpoint which does not have it supported.");
        User userBody = User.builder()
                .email(email)
                .name(name)
                .gender(gender)
                .status(status).build();
        //Note: i left test as failed due to the wrong implementation done at APi layer; for this situation, my expectation would be to receive 405 instead of 404 status code
        restApiClient.doPostRequestWithBody(405, apiUrl.concat("/").concat("11021"), userBody, bearer);
    }

    @Test
    @Order(2)
    public void retrieveDataForCreatedUser() {
        System.out.println("Scenario to validate data retrieval for previously created user");
        user = restApiClient.doGetRequest(200, apiUrl.concat("/").concat(userId), bearer)
                .then().extract().as(User.class, ObjectMapperType.GSON);
        Assertions.assertEquals(name, user.getName());
        Assertions.assertEquals(email, user.getEmail());
        Assertions.assertEquals(gender, user.getGender());
        Assertions.assertEquals(status, user.getStatus());
    }

    @Test
    @Order(2)
    public void retrieveDataForUnavailableResource() {
        System.out.println("Scenario to validate data retrieval when an unavailable resource is called");
        String expectedResult = "{message=Resource not found}";
        Response response = restApiClient.doGetRequest(404, apiUrl.concat("/").concat("999999999"), bearer);
        Assertions.assertEquals(expectedResult, response.jsonPath().get().toString());

    }

    @Test
    @Order(3)
    public void updateDataForCreatedUser() {
        System.out.println("Scenario to validate data update for previously created user");
        User userBody = User.builder()
                .email(email)
                .name(name.concat(" Update"))
                .gender("female")
                .status(status).build();
        user = restApiClient.doPutRequestWithBody(200, apiUrl.concat("/").concat(userId), userBody, bearer)
                .then().extract().as(User.class, ObjectMapperType.GSON);
        Assertions.assertEquals(name.concat(" Update"), user.getName());
        Assertions.assertEquals(email, user.getEmail());
        Assertions.assertEquals("female", user.getGender());
        Assertions.assertEquals(status, user.getStatus());
    }

    @Test
    @Order(4)
    public void removeCreatedUser() {
        System.out.println("Scenario to validate deletion for previously created user");
        restApiClient.doDeleteRequest(204, apiUrl.concat("/").concat(userId), bearer);
    }

    @Test
    @Order(4)
    public void removeCreatedUserAfterPreviousDeletion() {
        System.out.println("Scenario to validate deletion for previously deleted user");
        restApiClient.doDeleteRequest(404, apiUrl.concat("/").concat(userId), bearer);
    }
}
