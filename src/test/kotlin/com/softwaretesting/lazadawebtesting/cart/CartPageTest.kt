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
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import java.time.Duration


class CartPageTest {
    private lateinit var driver: WebDriver
    private lateinit var mainPage: MainPage
    private lateinit var dotenv: Dotenv
    private lateinit var duration: Duration
    private lateinit var wait: WebDriverWait

    @BeforeClass
    fun setUpClass() {
        driver = DriverFactory.createDriver()
        driver.manage().window().maximize()
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))

        duration = Duration.ofSeconds(10)
        wait = WebDriverWait(driver, duration)

        dotenv = dotenv()
    }

    @BeforeMethod
    fun setUpMethod() {
        val url = "https://lazada.co.id/#"
        driver.get(url)

        Thread.sleep(20000)

        MainPage(driver).loginButton.click()
        LoginHelper.login(LoginMethod.PASSWORD, driver, true)
        Thread.sleep(30000)
    }

    @AfterClass
    fun tearDownClass() {
        driver.quit()
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

    /**
     * TC_18 Remove item from cart.
     */
    @Test
    fun removeItemFromCart() {

        // Click cart button to open cart page
        val cartButton = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.lzd-nav-cart")
            )
        )
        cartButton.click()
        
        var deleteButtons = driver.findElements(By.xpath("//span[contains(@class,'automation-btn-delete')]"))

        // Iterate through each delete button
        while (deleteButtons.isNotEmpty()) {
            try {
                // Click the first delete button
                val deleteButton = deleteButtons[0]
                wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click()

                // Wait for the confirmation popup to appear
                val confirmPopup = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'next-dialog')]")
                    )
                )

                // Click the "HAPUS" button in the popup
                val deleteConfirmButton = confirmPopup.findElement(By.xpath(".//button[contains(text(),'HAPUS')]"))
                deleteConfirmButton.click()

                // Wait for the popup to disappear
                wait.until(ExpectedConditions.invisibilityOf(confirmPopup))

                // Wait for the cart to update
                Thread.sleep(1000)

                // Re-locate the delete buttons after each deletion
                deleteButtons = driver.findElements(By.xpath("//span[contains(@class,'automation-btn-delete')]"))
            } catch (e: Exception) {
                println("Failed to delete an item: ${e.message}")
                break
            }
        }

        // Verify the cart is empty
        val emptyCartMessage = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'cart-empty-text')]")
            )
        )

        val message = emptyCartMessage.text
        val actualMessage = "Tidak ada produk dalam troli ini"

        Assert.assertEquals(message, actualMessage, "Wrong text should be $actualMessage" )
    }
}