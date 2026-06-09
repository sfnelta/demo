package de.nelta.demo.api.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class BaseClient {
    protected RequestSpecification spec;

    public BaseClient(String baseUrl, int port) {
        this.spec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setPort(port)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }

    protected RequestSpecification given() {
        return io.restassured.RestAssured.given().spec(spec);
    }
}
