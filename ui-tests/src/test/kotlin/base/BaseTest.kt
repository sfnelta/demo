package base

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import io.github.bonigarcia.wdm.WebDriverManager

open class BaseTest {
    protected lateinit var driver: WebDriver

    @BeforeEach
    fun setUp() {
        WebDriverManager.chromedriver().setup()

        // Fixme: Avoids the built-in password manager 
        val options = ChromeOptions()
        options.addArguments("--incognito")
        options.addArguments("--headless")
        driver = ChromeDriver(options)

        driver.manage().window().maximize()
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}
