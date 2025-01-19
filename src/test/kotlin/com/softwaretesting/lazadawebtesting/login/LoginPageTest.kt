package com.softwaretesting.lazadawebtesting.login

import com.softwaretesting.helper.DriverFactory
import com.softwaretesting.helper.OAuthMethod
import com.softwaretesting.helper.OtpMethod
import com.softwaretesting.lazadawebtesting.FacebookContinueAsPage
import com.softwaretesting.lazadawebtesting.FacebookRegistrationPage
import com.softwaretesting.lazadawebtesting.MainPage
import com.softwaretesting.lazadawebtesting.registration.RegistrationPage
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.Assert
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import java.time.Duration

class LoginPageTest {
    private lateinit var driver: WebDriver
    private lateinit var loginPage: LoginPage
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

        MainPage(driver).loginButton.click()
        loginPage = LoginPage(driver)
        loginPage.switchToPhoneNumberMenu()
    }

    @AfterClass
    fun tearDownClass() {
        driver.quit()
    }

    /**
     * TC_08 Login using phone number and OTP.
     */
    @Test
    fun loginWithValidPhoneNumber() {
        val phoneNumber = dotenv.get("LAZADA_VALID_LOGIN_PHONE_NUMBER")
        loginPage.enterPhoneNumber(phoneNumber)
        loginPage.getOtp(OtpMethod.WHATSAPP)

        TODO("Unsolved, waiting for the OTP request block to expire")
    }
}