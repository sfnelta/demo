package de.nelta.demo.api.tests;

import de.nelta.demo.api.base.BaseTest;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class HealthTests extends BaseTest {

    @Test
    public void testHealthCheck() {
        io.restassured.RestAssured.given()
            .baseUri("http://localhost")
            .port(3004)
        .when()
            .get("/health")
        .then()
            .statusCode(200)
            .body("status", equalTo("UP and Running"))
            .body("uptime", containsString("seconds"))
            .body(matchesJsonSchemaInClasspath("schemas/health-response-schema.json"));
    }
}
