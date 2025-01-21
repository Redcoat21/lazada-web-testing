package com.softwaretesting.lazadawebtesting.cart

import com.softwaretesting.helper.DriverFactory
import com.softwaretesting.helper.LoginHelper
import com.softwaretesting.helper.LoginMethod
import com.softwaretesting.lazadawebtesting.MainPage
import com.softwaretesting.lazadawebtesting.login.LoginPage
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

class AddCartTest {
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
     * TC_17 Add item to cart from flash sale.
     */
    @Test
    fun addItemToCart() {
        // Find and click the first flash sale product
        val firstProduct = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'flash-sale-comp-container')]//div[@class='card-fs-content-body']//a[1]")
            )
        )
        firstProduct.click()

        // Wait for the product page to load completely
        wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='module_add_to_cart']"))
        )

        // Get initial cart quantity
        val cartQuantityElement = driver.findElement(By.xpath("//div[@class='lzd-nav-cart']/a/span[2]"))
        val initialQuantity = cartQuantityElement.text.toIntOrNull() ?: 0

        // Click add to cart button
        val addToCartButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='module_add_to_cart']//button[contains(@class, 'add-to-cart-buy-now-btn') and contains(@class, 'pdp-button_theme_orange')]")
            )
        )
        addToCartButton.click()

        // Wait for cart quantity to update and get new quantity
        Thread.sleep(2000) // Give some time for the cart to update
        val updatedCartQuantityElement = driver.findElement(By.xpath("//div[@class='lzd-nav-cart']/a/span[2]"))
        val newQuantity = updatedCartQuantityElement.text.toIntOrNull() ?: 0

        // Assert that the quantity has increased by 1
        Assert.assertEquals(newQuantity, initialQuantity + 1, "Cart quantity should increase by 1")

        Thread.sleep(10000)
    }
}