# Design Document: API Test Demo

## 1. Introduction
This document specifies the design for an API testing suite targeting the `restful-ecommerce` application. The goal is to provide a robust, scalable, and educational foundation for QA Engineers to learn and showcase Rest Assured, Java, and modern testing practices.

## 2. Technical Stack
- **Language:** Java 17 (Foundation for future Kotlin support).
- **Test Framework:** TestNG.
- **API Client:** Rest Assured.
- **Build Tool:** Maven.
- **Documentation:** Swagger/OpenAPI.
- **Data Handling:** JSON (org.json, Jackson for POJOs).
- **CI/CD:** Jenkins (Pipeline).
- **Environment:** Docker.

## 3. Architecture
The project follows a **Service Object Model (SOM)** pattern, similar to Page Object Model in UI testing.

### 3.1 Directory Structure
```text
api-tests/
├── src/
│   ├── main/
│   │   └── java/ (or kotlin/)
│   │       └── de.nelta.demo.api/
│   │           ├── client/         # API client wrappers (Service Objects)
│   │           ├── model/          # POJOs for Request/Response mapping
│   │           ├── config/         # Configuration management
│   │           └── utils/          # Helpers (Auth, Data generators)
│   └── test/
│       ├── java/
│       │   └── de.nelta.demo.api/
│       │       ├── tests/          # Functional test cases
│       │       └── base/           # Base test class for setup/teardown
│       └── resources/
│           ├── data/               # Static JSON test data
│           ├── schemas/            # JSON Schemas for validation
│           └── testng.xml          # Test execution suites
├── pom.xml                         # Project configuration
├── Dockerfile                      # Containerization for tests
└── Jenkinsfile                     # CI/CD pipeline definition
```

### 3.2 Service Objects (Clients)
Each major resource (e.g., `/orders`, `/auth`) should have a corresponding Client class that encapsulates the HTTP logic.
- Methods should return Rest Assured `Response` objects or deserialized POJOs.
- Use `RequestSpecBuilder` and `ResponseSpecBuilder` for common configurations (base URI, headers, logging).

### 3.3 Data Management
- Use JSON files in `src/test/resources/data` for complex payloads.
- Implement a `DataFactory` or use libraries like `JavaFaker` for dynamic data generation.
- Map JSON to POJOs using Jackson/Lombok for type-safe assertions.

## 4. Implementation Guidelines for Agents
1. **Base Configuration:** All tests must inherit from a `BaseTest` that initializes the environment (URL, Port) from a properties file or environment variables.
2. **Authentication:** Implement an `AuthFilter` or a centralized `TokenManager` to handle JWT retrieval and injection into secured requests (`/updateOrder`, `/deleteOrder`).
3. **Assertions:**
    - Use Rest Assured's Gherkin-style assertions (`then().assertThat()...`).
    - Use JSON Schema validation for all critical responses.
4. **Logging:** Enable `log().ifValidationFails()` by default to keep CI logs clean while providing detail on errors.
5. **Robustness:** Ensure tests are independent. Clean up created data in `@AfterMethod` or use unique identifiers for each test run.

## 5. Future Extensions
- **Kotlin Migration:** Transition Service Objects to Kotlin for more concise syntax.
- **Allure Reporting:** Add Allure for rich, interactive test reports.
- **Contract Testing:** Integrate Pact for consumer-driven contract testing.
