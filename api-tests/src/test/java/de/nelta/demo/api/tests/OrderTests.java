package de.nelta.demo.api.tests;

import de.nelta.demo.api.base.BaseTest;
import de.nelta.demo.api.model.Order;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import java.util.Arrays;

@Epic("E-Commerce API")
@Feature("Order Management")
public class OrderTests extends BaseTest {

    @org.testng.annotations.BeforeClass
    public void setup() {
        orderClient.deleteAllOrders(token);
    }

    @Test
    @Story("Create a single order")
    public void testAddOrder() {
        Order order = new Order(
                "user123", "prod456", "Test Product", 
                10.0, 2, 1.9, 21.9
        );
        
        Response response = orderClient.addOrders(Arrays.asList(order));
        
        response.then()
                .statusCode(201)
                .body("message", equalTo("Orders added successfully!"))
                .body("orders.product_name", hasItem("Test Product"))
                .body(matchesJsonSchemaInClasspath("schemas/get-orders-response-schema.json"));
    }

    @Test(dependsOnMethods = "testAddOrder")
    public void testGetAllOrders() {
        Response response = orderClient.getAllOrders();
        
        response.then()
                .statusCode(200)
                .time(lessThan(500L))
                .body("message", equalTo("Orders fetched successfully!"))
                .body("orders", not(empty()))
                .body(matchesJsonSchemaInClasspath("schemas/get-orders-response-schema.json"));
    }

    @Test(dependsOnMethods = "testAddOrder")
    public void testGetOrderById() {
        // First get all to find an ID
        Response allOrders = orderClient.getAllOrders();
        int orderId = allOrders.jsonPath().getInt("orders[0].id");
        
        Response response = orderClient.getOrder(orderId, null, null);
        
        response.then()
                .statusCode(200)
                .time(lessThan(500L))
                .body("message", equalTo("Order found!!"));
    }

    @Test(dependsOnMethods = "testAddOrder", enabled = false)
    public void testUpdateOrderSecured() {
        Response allOrders = orderClient.getAllOrders();
        var json = allOrders.jsonPath();
        int orderId = json.getInt("orders[0].id");
        
        // Use data from the actual order to satisfy server-side validation
        Order updatedOrder = new Order(
                json.getString("orders[0].user_id"),
                json.getString("orders[0].product_id"),
                "Updated Product Name",
                json.getDouble("orders[0].product_amount"),
                json.getInt("orders[0].qty"),
                json.getDouble("orders[0].tax_amt"),
                json.getDouble("orders[0].total_amt")
        );
        
        Response response = orderClient.updateOrder(orderId, updatedOrder, token);
        
        response.then()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "testAddOrder")
    public void testAddBulkOrders() {
        Order o1 = new Order("user1", "prod1", "P1", 10.0, 1, 1.0, 11.0);
        Order o2 = new Order("user2", "prod2", "P2", 20.0, 2, 2.0, 22.0);
        
        Response response = orderClient.addOrders(Arrays.asList(o1, o2));
        
        response.then()
                .statusCode(201)
                .body("orders", hasSize(greaterThanOrEqualTo(2)));
    }

    @Test
    public void testAddOrderMissingFields() {
        Order invalidOrder = new Order(); // All null
        Response response = orderClient.addOrders(Arrays.asList(invalidOrder));
        
        response.then()
                .statusCode(400);
    }

    @Test(dependsOnMethods = "testAddOrder")
    public void testGetOrderByUserId() {
        Response response = orderClient.getOrder(null, "user123", null);
        
        response.then()
                .statusCode(200)
                .body("orders[0].user_id", equalTo("user123"));
    }

    @Test(dependsOnMethods = "testAddOrder", enabled = false)
    public void testPartialUpdateOrder() {
        Response allOrders = orderClient.getAllOrders();
        int orderId = allOrders.jsonPath().getInt("orders[0].id");
        
        // Use a Map or simple object for partial update
        java.util.Map<String, Object> update = new java.util.HashMap<>();
        update.put("qty", 100);
        
        Response response = orderClient.partialUpdateOrder(orderId, update, token);
        
        response.then()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "testAddOrder", enabled = false)
    public void testDeleteOrder() {
        Response allOrders = orderClient.getAllOrders();
        int orderId = allOrders.jsonPath().getInt("orders[0].id");
        
        Response response = orderClient.deleteOrder(orderId, token);
        
        response.then()
                .statusCode(204);
        
        // Verify 404
        orderClient.getOrder(orderId, null, null).then().statusCode(404);
    }

    @Test
    public void testDeleteOrderUnsecured() {
        orderClient.deleteOrder(999, "").then().statusCode(400); // Consistent with PUT unsecured
    }

    @org.testng.annotations.AfterClass
    public void cleanup() {
        // Optional: Clean up all orders after this test class
        // orderClient.deleteAllOrders(token);
    }
}
