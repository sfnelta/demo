package de.nelta.demo.api.client;

import de.nelta.demo.api.model.Order;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.util.List;

public class OrderClient extends BaseClient {

    public OrderClient(String baseUrl, int port) {
        super(baseUrl, port);
    }

    @Step("Add bulk orders")
    public Response addOrders(List<Order> orders) {
        return given()
                .body(orders)
                .post("/addOrder");
    }

    @Step("Get all orders")
    public Response getAllOrders() {
        return given()
                .get("/getAllOrders");
    }

    @Step("Get order by parameters (id: {id}, user: {userId}, product: {productId})")
    public Response getOrder(Integer id, String userId, String productId) {
        var request = given();
        if (id != null) request.queryParam("id", id);
        if (userId != null) request.queryParam("user_id", userId);
        if (productId != null) request.queryParam("product_id", productId);
        return request.get("/getOrder");
    }

    @Step("Update order {id}")
    public Response updateOrder(int id, Order order, String token) {
        return given()
                .header("Authorization", "Bearer " + token)
                .body(order)
                .put("/updateOrder/" + id);
    }

    @Step("Partially update order {id}")
    public Response partialUpdateOrder(int id, Object partialOrder, String token) {
        return given()
                .header("Authorization", "Bearer " + token)
                .body(partialOrder)
                .patch("/partialUpdateOrder/" + id);
    }

    @Step("Delete order {id}")
    public Response deleteOrder(int id, String token) {
        return given()
                .header("Authorization", "Bearer " + token)
                .delete("/deleteOrder/" + id);
    }

    @Step("Delete all orders")
    public Response deleteAllOrders(String token) {
        return given()
                .header("Authorization", "Bearer " + token)
                .delete("/deleteAllOrders");
    }
}
