package base

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import io.github.bonigarcia.wdm.WebDriverManager

open class BaseTest {
    protected lateinit var driver: WebDriver

    @BeforeEach
    fun setUp() {
        WebDriverManager.chromedriver().setup()
        WebDriverManager.chromedriver().properties("--disable-web-security --incognito --guest")
        driver = ChromeDriver()
        driver.manage().window().maximize()
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}
