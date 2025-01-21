package com.softwaretesting.lazadawebtesting.login

import com.softwaretesting.helper.DriverFactory
import com.softwaretesting.helper.OAuthMethod
import com.softwaretesting.helper.OtpMethod
import com.softwaretesting.lazadawebtesting.MainPage
import com.softwaretesting.lazadawebtesting.facebook.FacebookContinueAsPage
import com.softwaretesting.lazadawebtesting.facebook.FacebookRegistrationPage
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.openqa.selenium.WebDriver
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
//        loginPage.switchToPhoneNumberMenu()
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
        val password = dotenv.get("LAZADA_VALID_LOGIN_PASSWORD")
        loginPage.enterPhoneOrEmail(phoneNumber)
        loginPage.enterPassword(password)
        loginPage.clickLogInButton()

        Thread.sleep(10000)

        TODO("Unsolved, waiting for the OTP request block to expire")
    }

    /**
     * TC_12 Login using valid facebook account.
     */
    @Test
    fun loginWithValidFacebookAccount() {
        loginPage.signUpWithOAuth(OAuthMethod.FACEBOOK)

        WebDriverWait(driver, duration).until {
            driver.windowHandles.size > 1
        }
        driver.switchTo().window(driver.windowHandles.last())

        // Wait until it change window
        wait.until(ExpectedConditions.urlContains("facebook.com"))
        val facebookRegistrationPage = FacebookRegistrationPage(driver)

        val facebookEmail = dotenv.get("FACEBOOK_EMAIL")
        val facebookPassword = dotenv.get("FACEBOOK_PASSWORD")

        println(facebookPassword)
        facebookRegistrationPage.submitData(facebookEmail, facebookPassword)

        // Wait until confirmation page. There's a chance that it will ask to solve captcha
        WebDriverWait(driver, Duration.ofMinutes(1)).until(ExpectedConditions.urlContains("/privacy/consent/gdp"))

        val facebookContinueAsPage = FacebookContinueAsPage(driver)
        facebookContinueAsPage.clickContinueAsButton()

        // Wait until the window close.
        wait.until(ExpectedConditions.numberOfWindowsToBe(1))

        driver.switchTo().window(driver.windowHandles.first())

        Thread.sleep(30 * 1000)
        Assert.assertTrue(driver.currentUrl?.contains("https://www.lazada.co.id/#") ?: false, "Should be redirected to the main page")
    }
}