package pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

/**
 * Page object representing the Checkout: Your Information step.
 */
class CheckoutPage(private val driver: WebDriver) {
    private val firstNameInput: WebElement
        get() = driver.findElement(By.id("first-name"))
    private val lastNameInput: WebElement
        get() = driver.findElement(By.id("last-name"))
    private val postalCodeInput: WebElement
        get() = driver.findElement(By.id("postal-code"))
    private val continueButton: WebElement
        get() = driver.findElement(By.id("continue"))

    /**
     * Fill in the customer information fields.
     */
    fun enterCustomerInfo(firstName: String, lastName: String, postalCode: String): CheckoutPage {
        firstNameInput.sendKeys(firstName)
        lastNameInput.sendKeys(lastName)
        postalCodeInput.sendKeys(postalCode)
        return this
    }

    /**
     * Proceed to the overview step.
     */
    fun continueToOverview(): CheckoutOverviewPage {
        continueButton.click()
        return CheckoutOverviewPage(driver)
    }
}

/**
 * Page object representing the Checkout: Overview step.
 */
class CheckoutOverviewPage(private val driver: WebDriver) {
    private val finishButton: WebElement
        get() = driver.findElement(By.id("finish"))

    /**
     * Finish the order.
     */
    fun finishOrder(): CheckoutCompletePage {
        finishButton.click()
        return CheckoutCompletePage(driver)
    }
}

/**
 * Page object representing the final Checkout Complete page.
 */
class CheckoutCompletePage(private val driver: WebDriver) {
    /**
     * Verify that the order was completed successfully.
     */
    fun isOrderComplete(): Boolean {
        val header = driver.findElement(By.className("complete-header")).text
        return header.contains("thank you for your order", ignoreCase = true)
    }
}

