package pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

/**
 * Page object representing the login page of the SauceDemo site.
 */
class LoginPage(private val driver: WebDriver) {
    private val usernameInput get() = driver.findElement(By.id("user-name"))
    private val passwordInput get() = driver.findElement(By.id("password"))
    private val loginButton get() = driver.findElement(By.id("login-button"))

    /** Open the SauceDemo login page. */
    fun open(): LoginPage {
        driver.get("https://www.saucedemo.com/")
        return this
    }

    /** Perform login and return InventoryPage. */
    fun login(username: String, password: String): pages.InventoryPage {
        usernameInput.sendKeys(username)
        passwordInput.sendKeys(password)
        loginButton.click()
        return pages.InventoryPage(driver)
    }
}
