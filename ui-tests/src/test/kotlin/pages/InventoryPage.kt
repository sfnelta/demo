package pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

class InventoryPage(private val driver: WebDriver) {
    private val firstAddToCartButton: WebElement
        get() = driver.findElement(By.cssSelector(".inventory_item button.btn_inventory"))

    private val cartIcon: WebElement
        get() = driver.findElement(By.id("shopping_cart_container"))

    fun addFirstItemToCart(): InventoryPage {
        firstAddToCartButton.click()
        return this
    }

    fun openCart(): CartPage {
        cartIcon.click()
        return CartPage(driver)
    }
}
