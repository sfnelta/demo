package tests

import base.BaseTest
import pages.LoginPage
import pages.InventoryPage
import pages.CartPage
import pages.CheckoutPage
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue

class ShoppingTest : BaseTest() {

    @Test
    fun completeShoppingFlow() {
        // Login
        val loginPage = LoginPage(driver)
        loginPage.open()
        val inventoryPage = loginPage.login("standard_user", "secret_sauce")

        // Add first product to cart
        inventoryPage.addFirstItemToCart()
        val cartPage = inventoryPage.openCart()

        // Verify cart contains the product
        val items = cartPage.getCartItems()
        assertTrue(items.isNotEmpty(), "Cart should contain at least one item")

        // Checkout
        val completePage = cartPage.proceedToCheckout()
            .enterCustomerInfo("John", "Doe", "12345")
            .continueToOverview()
            .finishOrder()
        assertTrue(completePage.isOrderComplete(), "Order should be completed")
    }
}
