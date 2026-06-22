package de.nelta.demo.api.tests;

import de.nelta.demo.api.base.BaseTest;
import de.nelta.demo.api.model.Order;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

@Epic("E-Commerce API")
@Feature("Order Management")
public class DeleteOrderTest extends BaseTest {

    private int orderIdToDelete;

    @BeforeClass
    public void setup() {
        // Create an order specifically for deletion
        Order order = new Order(
                "user-del", "prod-del", "Delete Product",
                15.0, 1, 1.0, 16.0
        );

        Response response = orderClient.addOrders(Arrays.asList(order));
        response.then().statusCode(201);
        
        // Retrieve the ID of the created order
        orderIdToDelete = response.jsonPath().getInt("orders[0].id");
    }

    @Test
    public void testDeleteOrder() {
        Response response = orderClient.deleteOrder(orderIdToDelete, token);
        
        response.then()
                .statusCode(204);
        
        // Verify 404
        orderClient.getOrder(orderIdToDelete, null, null).then().statusCode(404);
    }

    @AfterClass
    public void cleanup() {
        // Cleanup all orders, including any others added during setup
        orderClient.deleteAllOrders(token);
    }
}
