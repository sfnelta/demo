# UI‑Tests Module Design Documents

## 1. Technical Documentation (Module Overview)

- **Artifact**: `ui-tests/pom.xml` (inherits from parent multi‑module pom).
- **Purpose**: Demonstrate end‑to‑end UI testing with Selenium + Kotlin on the public demo site `https://www.saucedemo.com/`.
- **Dependencies**:
  - `org.seleniumhq.selenium:selenium-java` (latest stable)
  - `org.seleniumhq.selenium:selenium-support`
  - `org.junit.jupiter:junit-jupiter-api` & `junit-jupiter-engine`
  - `org.jetbrains.kotlin:kotlin-stdlib-jdk8`
  - `org.jetbrains.kotlin:kotlin-reflect`
  - `io.github.bonigarcia:webdrivermanager` (auto‑download drivers)
- **Build configuration**:
  - Use Maven Compiler Plugin for Java 17 and Kotlin Maven Plugin for Kotlin 1.9.
  - Enable Surefire/Failsafe for test execution.
  - Configure Maven profiles for `chrome` and `firefox` browsers.
- **Directory layout**:
  ```
  ui-tests/
    src/main/kotlin/    # optional helper code
    src/test/kotlin/    # test classes
    src/test/resources/ # static data (e.g., test data JSON)
  ```
- **Continuous Integration**: Sample GitHub Actions workflow that spins up Chrome/Firefox via Selenium Grid.

## 2. Test Concept (SauceDemo Scenario)

| Feature | Test Flow | Key Assertions | Data Sources |
|---------|-----------|----------------|--------------|
| **Login** | Open URL → enter `standard_user` / `secret_sauce` → click Login | Verify landing on inventory page (URL contains `/inventory.html`) | Hard‑coded credentials (can be externalised later) |
| **Add to Cart** | From inventory, select first product → click *Add to cart* → open Cart | Cart badge shows `1`; product appears in cart list | Product name from UI (dynamic) |
| **Checkout** | Click *Checkout* → fill first‑name/last‑name/zip → continue → finish | Confirmation page shows `THANK YOU FOR YOUR ORDER` | Static user data |
| **Logout** | Click menu → Logout | Return to login page | – |

### Test Design Patterns
- **Page Object Model (POM)** – separate Kotlin classes per page (`LoginPage`, `InventoryPage`, `CartPage`, `CheckoutPage`).
- **Fluent API** – chain actions for readability.
- **Data‑Driven Tests** – JUnit5 `@ParameterizedTest` with CSV/JSON for different user roles (e.g., `locked_out_user`).
- **Cross‑Browser** – Maven profile selects driver (`chrome`/`firefox`).
- **Reporting** – Use `Allure` or `ExtentReports` for HTML reports.

## 3. Kotlin Support (Integration Plan)

1. **Add Kotlin Maven Plugin** to `ui-tests/pom.xml`:
   ```xml
   <plugin>
     <groupId>org.jetbrains.kotlin</groupId>
     <artifactId>kotlin-maven-plugin</artifactId>
     <version>${kotlin.version}</version>
     <executions>
       <execution>
         <id>compile</id>
         <phase>compile</phase>
         <goals><goal>compile</goal></goals>
       </execution>
       <execution>
         <id>test-compile</id>
         <phase>test-compile</phase>
         <goals><goal>test-compile</goal></goals>
       </execution>
     </executions>
   </plugin>
   ```
2. **Set Kotlin version** property in parent pom (`kotlin.version` = `1.9.22`).
3. **Configure source directories** (`src/main/kotlin`, `src/test/kotlin`). Maven automatically includes them when using the plugin.
4. **Add Kotlin‑specific linting** – e.g., `detekt` via Maven plugin (optional). Provide a sample `detekt.yml`.
5. **Gradle fallback** – not required; keep Maven as the single build system.
6. **Testing framework** – JUnit 5 works natively with Kotlin; no extra adapters needed.
7. **Documentation** – Update README with Kotlin usage instructions and example `mvn test -Pchrome`.

---

**Next Steps for Implementation Agents**
1. Create/modify `ui-tests/pom.xml` with the dependencies and plugins listed above.
2. Add the page‑object Kotlin classes under `src/test/kotlin`.
3. Implement the test scenarios using the described flow.
4. Add CI workflow file (`.github/workflows/ui-tests.yml`).
5. Write a concise module README that references this design document.

*All artefacts are located in this repository under `ui-tests`.*
