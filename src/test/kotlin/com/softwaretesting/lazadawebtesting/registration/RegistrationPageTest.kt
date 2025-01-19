package com.softwaretesting.lazadawebtesting.registration

import com.softwaretesting.helper.DriverFactory
import com.softwaretesting.helper.OAuthMethod
import com.softwaretesting.helper.OtpMethod
import com.softwaretesting.lazadawebtesting.FacebookContinueAsPage
import com.softwaretesting.lazadawebtesting.FacebookRegistrationPage
import com.softwaretesting.lazadawebtesting.MainPage
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

class RegistrationPageTest {
    private lateinit var driver: WebDriver
    private lateinit var registrationPage: RegistrationPage
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

        MainPage(driver).signUpButton.click()
        registrationPage = RegistrationPage(driver)
    }

    @AfterClass
    fun tearDownClass() {
        driver.quit()
    }

    /**
     * TC_01 Register with a valid phone number.
     */
    @Test
    fun registerWithValidPhoneNumber() {
        val phoneNumber = dotenv.get("LAZADA_VALID_REGISTRATION_PHONE_NUMBER")
        registrationPage.enterPhoneNumber(phoneNumber)
        registrationPage.checkTermsAndConditions()
        registrationPage.getOtp(OtpMethod.WHATSAPP)

        val otpInputsCell: List<WebElement> = wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='iweb-passcode-input-cell-container']//div[@class='iweb-passcode-input-cell']"))
        )

        // Wait until the OTP input fields are filled.
        WebDriverWait(driver, Duration.ofMinutes(3)).until {
            otpInputsCell.all { it.text.isNotEmpty() }
        }

        TODO("Unsolved, waiting for the OTP request block to expire")
    }

    /**
     * TC_02 Register with an invalid phone number.
     */
    @Test
    fun registerWithInvalidPhoneNumber() {
        val phoneNumber = dotenv.get("LAZADA_INVALID_REGISTRATION_PHONE_NUMBER")
        registrationPage.enterPhoneNumber(phoneNumber)
        registrationPage.checkTermsAndConditions()
        registrationPage.getOtp(OtpMethod.WHATSAPP)

        val errorToast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".iweb-toast-wrap")))
        val expectedToastMessage = "Please enter a valid phone number."
        Assert.assertEquals(errorToast.text, expectedToastMessage, "Error message should be $expectedToastMessage")
    }

    /**
     * TC_03 Register without checking the terms and conditions.
     */
    @Test
    fun registerWithoutCheckingTermsAndConditions() {
        val phoneNumber = dotenv.get("LAZADA_VALID_REGISTRATION_PHONE_NUMBER")
        registrationPage.enterPhoneNumber(phoneNumber)
        registrationPage.getOtp(OtpMethod.WHATSAPP)

        val errorToast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".iweb-toast-wrap")))
        val expectedToastMessage = "You should agree to our Terms of Use and Privacy Policy."
        Assert.assertEquals(errorToast.text, expectedToastMessage, "Error message should be $expectedToastMessage")
    }

    /**
     * TC_06 Register with valid facebook account.
     */
    @Test
    fun registerWithValidFacebookAccount() {
        registrationPage.checkTermsAndConditions()
        registrationPage.signUpWithOAuth(OAuthMethod.FACEBOOK)

        WebDriverWait(driver, duration).until {
            driver.windowHandles.size > 1
        }
        driver.switchTo().window(driver.windowHandles.last())

        // Wait until it change window
        wait.until(ExpectedConditions.urlContains("facebook.com"))
        val facebookRegistrationPage = FacebookRegistrationPage(driver)

        val facebookEmail = dotenv.get("FACEBOOK_EMAIL")
        val facebookPassword = dotenv.get("FACEBOOK_PASSWORD")

        facebookRegistrationPage.submitData(facebookEmail, facebookPassword)

        // Wait until confirmation page. There's a chance that it will ask to solve captcha
        WebDriverWait(driver, Duration.ofMinutes(1)).until(ExpectedConditions.urlContains("/privacy/consent/gdp"))

        val facebookContinueAsPage = FacebookContinueAsPage(driver)
        facebookContinueAsPage.clickContinueAsButton()

        // Wait until the window close.
        wait.until(ExpectedConditions.numberOfWindowsToBe(1))

        driver.switchTo().window(driver.windowHandles.first())

        TODO("Unsolved, Captcha should appear after finishing the facebook registration")
        try {
            // Try if captcha exist, then wait for it.
            wait.until {
                registrationPage.waitForCaptcha()
            }
        } catch(e: Exception) {
            // Do nothing
            e.printStackTrace()
        }

        Assert.assertTrue(driver.currentUrl?.contains("https://www.lazada.co.id/#") ?: false, "Should be redirected to the main page")
    }

    /**
     * TC_07 Register with registered facebook account.
     */
    @Test
    fun registerWithRegisteredFacebookAccount() {
        TODO("Unsolved, Test result is it will still succeed. Need further confirmation")
    }
}