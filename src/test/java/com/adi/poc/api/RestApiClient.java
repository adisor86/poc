package com.adi.poc.api;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RestApiClient {
    public Response doPostRequestWithBody(int status, String url, Object body, String bearer) {
        Response response;
        String bodyParam = new Gson().toJson(body);
        RequestSpecification requestSpecification = buildRequestWithBody(bodyParam);
        requestSpecification.header("Authorization", "Bearer " + bearer);
        response = requestSpecification.log().all().post(url);
        validateResponseStatus(status, response);
        return response;
    }

    public Response doPutRequestWithBody(int status, String url, Object body, String bearer) {
        Response response;
        String bodyParam = new Gson().toJson(body);
        RequestSpecification requestSpecification = buildRequestWithBody(bodyParam);
        requestSpecification.header("Authorization", "Bearer " + bearer);
        response = requestSpecification.log().all().put(url);
        validateResponseStatus(status, response);
        return response;
    }

    public Response doGetRequest(int status, String url, String bearer) {
        Response response;
        RequestSpecification requestSpecification = RestAssured.given()
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + bearer);
        response = requestSpecification.log().all().get(url);
        validateResponseStatus(status, response);
        return response;
    }

    public Response doDeleteRequest(int status, String url, String bearer) {
        Response response;
        RequestSpecification requestSpecification = RestAssured.given()
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + bearer);
        response = requestSpecification.log().all().delete(url);
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
