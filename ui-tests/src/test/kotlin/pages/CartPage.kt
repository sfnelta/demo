package pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class CartPage(private val driver: WebDriver) {
    private val checkoutButton: WebElement
        get() = driver.findElement(By.id("checkout"))

    fun proceedToCheckout(): CheckoutPage {
        checkoutButton.click()
        return CheckoutPage(driver)
    }

    fun getCartItems(): List<String> {
        val items = driver.findElements(By.className("cart_item"))
        return items.map { it.findElement(By.className("inventory_item_name")).text }
    }

    fun hasItems(): Boolean = getCartItems().isNotEmpty()
}
