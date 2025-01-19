package com.softwaretesting.lazadawebtesting

import com.softwaretesting.helper.DriverFactory
import com.softwaretesting.helper.OtpMethod
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import java.time.Duration

class RegistrationPageTest {
    private lateinit var driver: WebDriver
    private lateinit var registrationPage: RegistrationPage
    private lateinit var dotenv: Dotenv

    @BeforeClass
    fun setUpClass() {
        driver = DriverFactory.createDriver()
        driver.manage().window().maximize()
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10))

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
        val phoneNumber = dotenv.get("LAZADA_REGISTRATION_PHONE_NUMBER")
        registrationPage.enterPhoneNumber(phoneNumber)
        registrationPage.checkTermsAndConditions()
        registrationPage.getOtp(OtpMethod.WHATSAPP)

        val otpInputsCell: List<WebElement> = WebDriverWait(driver, Duration.ofSeconds(5)).until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='iweb-passcode-input-cell-container']//div[@class='iweb-passcode-input-cell']"))
        )

        // Wait until the OTP input fields are filled.
        WebDriverWait(driver, Duration.ofMinutes(3)).until {
            otpInputsCell.all { it.text.isNotEmpty() }
        }

        TODO("Unsolved, waiting for the OTP request block to expire")
    }
}