package com.softwaretesting.lazadawebtesting.login

import com.softwaretesting.helper.OAuthMethod
import com.softwaretesting.helper.OtpMethod
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

// page_url = https://www.lazada.co.id
class LoginPage(driver: WebDriver) {
    @FindBy(xpath = "//div[contains(text(), 'Phone Number')]")
    lateinit var phoneNumberMenu: WebElement

    @FindBy(xpath = "(//input[@class='iweb-input'])[last()]")
    lateinit var phoneNumberInput: WebElement

    @FindBy(xpath = "(//div[@class='iweb-button-mask'])[1]")
    lateinit var whatsappOtpButton: WebElement

    @FindBy(xpath = "(//div[@class='iweb-button-mask'])[last()]")
    lateinit var smsOtpButton: WebElement

    @FindBy(xpath = "(//div[@class='index_module_buttonItem__8ea6d5ec'])[1]")
    lateinit var googleButton: WebElement

    @FindBy(xpath = "(//div[@class='index_module_buttonItem__8ea6d5ec'])[last()]")
    lateinit var facebookButton: WebElement

    /**
     * Switch to phone number menu
     */
    fun switchToPhoneNumberMenu() {
        phoneNumberMenu.click()
    }

    /**
     * Enter phone number to the phone number input field
     * @param phoneNumber phone number to be entered
     */
    fun enterPhoneNumber(phoneNumber: String) {
        phoneNumberInput.sendKeys(phoneNumber)
    }


    /**
     * Click the OTP button based on the given method. Clicking this button will submit the registration form.
     * @param otpMethod The OTP method to be used, it should be either Whatsapp or SMS.
     */
    fun getOtp(otpMethod: OtpMethod) {
        when (otpMethod) {
            OtpMethod.WHATSAPP -> whatsappOtpButton.click()
            OtpMethod.SMS -> smsOtpButton.click()
        }
    }

    /**
     * Sign up with OAuth method.
     * @param oAuthMethod The OAuth method to be used, it should be either Google or Facebook.
     */
    fun signUpWithOAuth(oAuthMethod: OAuthMethod) {
        when (oAuthMethod) {
            OAuthMethod.GOOGLE -> googleButton.click()
            OAuthMethod.FACEBOOK -> facebookButton.click()
        }
    }

    init {
        PageFactory.initElements(driver, this)
    }
}