# Test Concept: restful-ecommerce API

## 1. Objectives
- Verify functional correctness of all CRUD operations for Orders.
- Ensure security constraints (JWT) are enforced on protected endpoints.
- Validate data integrity and schema compliance.
- Demonstrate modern API testing patterns using Rest Assured.

## 2. Test Scope
The suite covers the following endpoints from the `restful-ecommerce` application:
- `POST /auth`: Authentication and Token generation.
- `GET /health`: System health monitoring.
- `POST /addOrder`: Order creation (Bulk and Single).
- `GET /getAllOrders`: Data retrieval.
- `GET /getOrder`: Search functionality.
- `PUT /updateOrder/{id}`: Full resource update (Secured).
- `PATCH /partialUpdateOrder/{id}`: Partial resource update (Secured).
- `DELETE /deleteOrder/{id}`: Resource removal (Secured).
- `POST /imageUpload`: Multipart/form-data handling (Secured).

## 3. Test Scenarios

### 3.1 Authentication
- **Positive:** Valid credentials return 201 and a JWT.
- **Negative:** Missing credentials return 400.
- **Negative:** Invalid credentials return 401.

### 3.2 Orders (Functional)
- **Creation:**
    - Create a single order and verify it appears in `getAllOrders`.
    - Create multiple orders in one request (Bulk).
    - Validate 400 error for missing required fields (e.g., `user_id`).
- **Retrieval:**
    - Get order by `id`.
    - Get order by `user_id`.
    - Handle 404 for non-existent IDs.
- **Modification:**
    - Update an existing order with valid JWT.
    - Partially update (PATCH) an order field (e.g., `qty`).
    - Negative: Update order without JWT (403).
    - Negative: Update order with invalid JWT (400/401).
- **Deletion:**
    - Delete an order and verify it's gone.
    - Negative: Delete without JWT.

### 3.3 Advanced Scenarios
- **Schema Validation:** Ensure all 200/201 responses match the predefined JSON Schemas.
- **Health Check:** Verify `uptime` and `status` values are sensible.
- **Image Upload:** Upload a valid PNG file and verify 200 response.

## 4. Test Data Strategy
- **Static:** `singleOrder.json` for baseline tests.
- **Dynamic:** Use unique `user_id` and `product_id` for each test run to avoid collisions.
- **Setup/Teardown:** The `deleteAllOrders` endpoint (Secured) should be used cautiously to reset the environment in non-production stages.

## 5. Success Criteria
- 100% pass rate for functional scenarios.
- All secured endpoints return 403 when no token is provided.
- Response times for GET requests < 500ms (Performance baseline).
- Automated report generated after every execution.
