import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RestAssuredDemo {
    @BeforeClass
    public void setup() {
        // RestAssured.baseURI = "http://localhost";
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        // RestAssured.port = 3004;
    }

    @Test
    public void testGetRequest() {
        // Override default config
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and save the response to operate on the response
        Response response = given()
                .when()
                .get("/posts/1")
                .then()
                .extract()
                .response();

        // This is  one of the ways to log response bodies
        System.out.println("Response JSON: " + response.asString()); // Verify that the status code is 200.
        
        response.then().statusCode(HttpStatus.SC_OK);

        // Parses the body to perform equality checks
        response.then().body("userId", equalTo(1));
        response.then().body("id", equalTo(1));
        response.then().body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));
        response.then().body("body", equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
    }

    @Test
    public void getPostOneRequest() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given().
        when().
            get("/posts/1").
        then().
            assertThat().
            statusCode(HttpStatus.SC_OK).
            body("userId", equalTo(1)).
            body("id", equalTo(1)).
            body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit")).
            body("body", equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
    }

    @Test
    public void authenticate() {

    }
}