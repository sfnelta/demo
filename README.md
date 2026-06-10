# Demo Projects

This repository is a showcase for Software Quality Assurance technologies, including Java, Kotlin, Maven, Jenkins, Git, and Docker. It serves as a learning foundation and a platform for presenting modern testing architectures to QA Engineering teams.

## Prerequisites
1. **Java 17+**
1. **Maven 3.8+**
1. **Git**
1. **Docker** (optional, for containerized execution)
1. **Target App**: `restful-ecommerce` running on `http://localhost:3004`.

## Modules

### 1. API Tests (`api-tests`)
A robust API testing suite targeting the `restful-ecommerce` Node.js application.

*   **Architecture**: Service Object Model (SOM).
*   **Technologies**: Rest Assured, TestNG, Jackson (POJO mapping), JSON Schema Validator.
*   **Features**: 
    *   Centralized Authentication (JWT) management.
    *   Full CRUD operation coverage.
    *   Strict JSON Schema validation for all responses.
    *   Performance assertions (Response time < 500ms).
*   **Running API Tests**:
    ```powershell
    mvn clean test -pl api-tests
    ```
*   **Generating Reports**:
    ```powershell
    mvn allure:report -pl api-tests
    ```
    Report will be in: `api-tests/target/site/allure-maven-plugin/index.html`

*   **Key Files**:
    *   `DESIGN.md`: Architectural specifications.
    *   `TEST_CONCEPT.md`: Detailed test scenarios and objectives.
    *   `src/test/resources/testng.xml`: Test suite orchestration.

### 2. Performance Tests (`performance-tests`)
Gatling-based performance tests for high-load simulation.

*   **Running Performance Tests**:
    ```powershell
    mvn gatling:test -pl performance-tests
    ```

### 3. UI Tests (`ui-tests`)
*(In development)*

## Build the Entire Project
To build all modules and install them to your local repository:
```powershell
mvn clean install
```

## Documentation
*   [API Design Document](api-tests/DESIGN.md)
*   [API Test Concept](api-tests/TEST_CONCEPT.md)
