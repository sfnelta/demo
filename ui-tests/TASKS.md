# UI‑Tests Implementation Tasks

- [x] Add Kotlin Maven plugin and dependencies to pom.xml (already done).
- [x] Create base test class BaseTest.kt with WebDriver setup/teardown.
- [x] Implement Page Objects:
  - [x] LoginPage.kt
  - [x] InventoryPage.kt
  - [x] CartPage.kt
  - [x] CheckoutPage.kt
- [x] Write end‑to‑end test ShoppingTest.kt covering login, add to cart, checkout, logout.
- [x] Validate tests run and pass (`mvn test -pl ui-tests` → 1/1 passing).
  - Fixed syntax errors in ShoppingTest.kt (spaces in function name, unquoted strings).
  - Fixed single-quote strings in InventoryPage.kt.
  - Fixed Surefire include pattern (`**/*Test.kt` → `**/*Test`) to match compiled classes.
  - Added explicit `sourceDirs` to Kotlin Maven Plugin configuration.
  - Fixed `isOrderComplete()` to use case-insensitive text comparison.
- [ ] Add CI workflow .github/workflows/ui-tests.yml.
- [x] Update module README with usage instructions.

When a task is completed, replace the checkbox with x.
