package de.nelta.demo.api.client;

import de.nelta.demo.api.model.AuthRequest;
import de.nelta.demo.api.model.AuthResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class AuthClient extends BaseClient {

    public AuthClient(String baseUrl, int port) {
        super(baseUrl, port);
    }

    @Step("Login with username: {request.username}")
    public Response login(AuthRequest request) {
        return given()
                .body(request)
                .post("/auth");
    }

    @Step("Obtain JWT token for user: {username}")
    public String getToken(String username, String password) {
        AuthRequest request = new AuthRequest(username, password);
        return login(request)
                .then()
                .statusCode(201)
                .extract()
                .as(AuthResponse.class)
                .getToken();
    }
}
