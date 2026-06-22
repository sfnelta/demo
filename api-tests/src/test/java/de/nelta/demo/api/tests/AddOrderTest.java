package de.nelta.demo.api.tests;

import de.nelta.demo.api.base.BaseTest;
import de.nelta.demo.api.model.Order;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

@Epic("E-Commerce API")
@Feature("Order Management")
public class AddOrderTest extends BaseTest {

    @BeforeClass
    public void setup() {
        // Ensure a clean state before running the test
        orderClient.deleteAllOrders(token);
    }

    @Test
    @Story("Create a single order")
    public void testAddOrder() {
        Order order = new Order(
                "user123", "prod456", "Test Product",
                10.0, 2, 1.9, 21.9);

        Response response = orderClient.addOrders(Arrays.asList(order));

        response.then()
                .statusCode(201)
                .body("message", equalTo("Orders added successfully!"))
                .body("orders.product_name", hasItem("Test Product"))
                .body(matchesJsonSchemaInClasspath("schemas/get-orders-response-schema.json"));
    }

    @AfterClass
    public void cleanup() {
        // Clean up the created order(s)
        orderClient.deleteAllOrders(token);
    }
}
