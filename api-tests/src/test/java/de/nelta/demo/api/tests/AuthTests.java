package de.nelta.demo.api.tests;

import de.nelta.demo.api.base.BaseTest;
import de.nelta.demo.api.model.AuthRequest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class AuthTests extends BaseTest {

    @Test
    public void testLoginSuccess() {
        AuthRequest request = new AuthRequest("admin", "secretPass123");
        Response response = authClient.login(request);
        
        response.then()
                .statusCode(201)
                .body("message", equalTo("Authentication Successful!"))
                .body("token", not(emptyString()))
                .body(matchesJsonSchemaInClasspath("schemas/auth-response-schema.json"));
    }

    @Test
    public void testLoginFailureInvalidCredentials() {
        AuthRequest request = new AuthRequest("wrong", "wrong");
        Response response = authClient.login(request);
        
        response.then()
                .statusCode(401);
    }
}
