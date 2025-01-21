package com.softwaretesting.lazadawebtesting.cart

import com.softwaretesting.helper.DriverFactory
import com.softwaretesting.helper.LoginHelper
import com.softwaretesting.helper.LoginMethod
import com.softwaretesting.lazadawebtesting.MainPage
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.Assert
import org.testng.annotations.AfterClass
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import java.time.Duration

class RemoveCartTest {
    private lateinit var driver: WebDriver
    private lateinit var mainPage: MainPage
    private lateinit var dotenv: Dotenv
    private lateinit var duration: Duration
    private lateinit var wait: WebDriverWait

    @BeforeMethod
    fun setupMethod() {
        driver = DriverFactory.createDriver()
        driver.manage().window().maximize()
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))

        duration = Duration.ofSeconds(10)
        wait = WebDriverWait(driver, duration)

        val url = "https://lazada.co.id/#"
        driver.get(url)
        mainPage = MainPage(driver)

        dotenv = dotenv()
    }

    @AfterMethod
    fun teardownMethod() {
        driver.quit()
    }

    @BeforeMethod
    fun setUpMethod() {
        val url = "https://lazada.co.id/#"
        driver.get(url)

        MainPage(driver).loginButton.click()
        LoginHelper.login(LoginMethod.PASSWORD, driver, true)
        Thread.sleep(5000)
    }

    /**
     * TC_18 Remove item from cart.
     */
    @Test
    fun removeItemFromCart() {

        // Click cart button to open cart page
        val cartButton = WebDriverWait(driver, Duration.ofMinutes(3)).until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//span[@id='topActionCartNumber']/ancestor::a")
            )
        )
        cartButton.click()

        // Get the first product name before removing
        val firstProductElement = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//a[@class='automation-link-from-title-to-prod title'])[1]")
            )
        )
        val firstProductName = firstProductElement.text

        // Wait for the cart page to load and get the first product name in cart
        val cartProductElement = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//a[@class='automation-link-from-title-to-prod title'])[1]")
            )
        )
        val cartProductName = cartProductElement.text

        // Click delete button for the first product
        val deleteButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("(//span[@class='automation-btn-delete']/span)[1]")
            )
        )
        deleteButton.click()

        // Wait for the confirmation modal and click delete
        val confirmDeleteButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='dialog-footer-5']/button[contains(@class, 'next-btn-primary')]")
            )
        )
        confirmDeleteButton.click()

        // Wait for the delete operation to complete and verify the product is removed
        Thread.sleep(2000) // Give some time for the deletion to complete

        // Verify that the removed product is not the same as the first product now
        try {
            val newFirstProductElement = driver.findElement(
                By.xpath("(//a[@class='automation-link-from-title-to-prod title'])[1]")
            )
            val newFirstProductName = newFirstProductElement.text
            Assert.assertNotEquals(newFirstProductName, firstProductName, "Product should be removed from cart")
        } catch (e: Exception) {
            // If no products are found, that's also a valid case
            // It means the cart is empty after removal
        }
    }
}
