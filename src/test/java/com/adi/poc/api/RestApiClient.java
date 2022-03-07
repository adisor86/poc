package com.adi.poc.api;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.springframework.stereotype.Service;

@Service
public class RestApiClient {
    public <T> Response doPostWithBody(int status, String url, T body, String bearer) {
        Response response;
        String bodyParam = new Gson().toJson(body);
        RequestSpecification requestSpecification = buildRequestWithBody(bodyParam);
        requestSpecification.header("Authorization", "Bearer " + bearer);
        response = requestSpecification.post(url);
        validateResponseStatus(status, response);
        return response;
    }

    public Response getWithPathParameter(int status, String url, String bearer) {
        Response response;
        RequestSpecification requestSpecification = RestAssured.given()
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + bearer);
        response = requestSpecification.get(url);
        validateResponseStatus(status, response);
        return response;
    }

    private void validateResponseStatus(int status, Response response) {
        try {
            Assertions.assertEquals(status, response.getStatusCode());
        } catch (Exception e) {
            System.out.println("Error in retrieving API response.");
            throw new RuntimeException(e);
        }
    }

    private RequestSpecification buildRequestWithBody(String bodyParam) {
        RequestSpecification requestSpecification = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
        if (bodyParam != null) {
            requestSpecification = requestSpecification.body(bodyParam);
        }
        return requestSpecification;
    }
}
