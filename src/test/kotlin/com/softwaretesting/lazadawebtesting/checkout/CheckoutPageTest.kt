package com.softwaretesting.lazadawebtesting.checkout

import com.softwaretesting.helper.CartHelper
import com.softwaretesting.helper.DriverFactory
import com.softwaretesting.helper.LoginHelper
import com.softwaretesting.helper.LoginMethod
import com.softwaretesting.lazadawebtesting.MainPage
import com.softwaretesting.lazadawebtesting.cart.CartPage
import com.softwaretesting.lazadawebtesting.login.LoginPage
import io.github.cdimascio.dotenv.Dotenv
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.Assert
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import java.time.Duration

class CheckoutPageTest {
    private lateinit var driver: WebDriver
    private lateinit var cartPage: CartPage
    private lateinit var dotenv: Dotenv
    private lateinit var duration: Duration
    private lateinit var wait: WebDriverWait

    @BeforeMethod
    fun setupMethod() {
        driver = DriverFactory.createDriver()
        driver.manage().window().maximize()
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))

        duration = Duration.ofSeconds(10)

        val url = "https://lazada.co.id/#"
        driver.get(url)

        wait = WebDriverWait(driver, duration)

        // Login
        MainPage(driver).loginButton.click()
        LoginHelper.login(LoginMethod.PASSWORD, driver, true)

        Thread.sleep(5 * 1000)

        CartHelper.addToCart(driver)
    }

    @AfterMethod
    fun teardownMethod() {
        driver.quit()
    }

    /**
     * TC_19 Successful checkout with QRIS.
     */
    @Test
    fun test() {
        // Go to the cart page.
        val cartButton = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.lzd-nav-cart")
            )
        )
        cartButton.click()
        wait.until(ExpectedConditions.urlContains("cart"))

        cartPage.checkCheckbox(1)

        val checkoutButton = driver.findElement(By.cssSelector(".checkout-order-total-button"))
        checkoutButton.click()

        wait.until(ExpectedConditions.urlContains("checkout"))

        val paymentMethod = driver.findElement(By.cssSelector(".payment-card-header-action"))
        paymentMethod.click()

        // The container
        val paymentMethods = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".methods")))
        paymentMethods.findElement(By.xpath("//*[text()='QRIS']"))

        val payButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn")))
        payButton.click()

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@text()='Buat pesanan']"))).click()

        wait.until(ExpectedConditions.urlContains("paymen-cashier-result"))

        val qrCode = driver.findElement(By.cssSelector(".barcode"))
        Assert.assertTrue(qrCode.isDisplayed)
    }
}