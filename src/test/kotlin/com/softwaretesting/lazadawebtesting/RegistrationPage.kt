package com.softwaretesting.lazadawebtesting

import com.softwaretesting.helper.OAuthMethod
import com.softwaretesting.helper.OtpMethod
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

// page_url = https://www.lazada.co.id/#?
class RegistrationPage(driver: WebDriver) {
    @FindBy(xpath = "(//div[@class='iweb-input-container']//input[@type='text' and @placeholder='Enter your phone number'])[last()]")
    lateinit var phoneNumberField: WebElement

    @FindBy(css = "div[class$='iweb-checkbox-icon--box']")
    lateinit var termsAndConditionsCheckbox: WebElement

    @FindBy(css = "button[class*='iweb-button-primary'] div[class='iweb-button-mask']")
    lateinit var whatsappOtpButton: WebElement

    @FindBy(css = "button[class*='iweb-button-secondary'] div[class='iweb-button-mask']")
    lateinit var smsOtpButton: WebElement

    // Get the first one
    @FindBy(xpath = "(//div[@class='index_module_buttonItem__8ea6d5ec'])[1]")
    lateinit var googleButton: WebElement

    @FindBy(xpath = "(//div[@class='index_module_buttonItem__8ea6d5ec'])[last()]")
    lateinit var facebookButton: WebElement

    @FindBy(id = "myAccountTrigger")
    lateinit var myAccountTrigger: WebElement


    /**
     * Enter phone number to the registration form.
     * @param phoneNumber The phone number to be entered.
     */
    fun enterPhoneNumber(phoneNumber: String) {
        phoneNumberField.sendKeys(phoneNumber)
    }

    /**
     * Check the terms and conditions checkbox.
     */
    fun checkTermsAndConditions() {
        termsAndConditionsCheckbox.click()
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

    /**
     * Wait until the captcha is solved. (Manually). This method should be awaited inside WebDriverWait
     */
    fun waitForCaptcha(): Boolean {
        return myAccountTrigger.text.isNotEmpty()
    }

    init {
        PageFactory.initElements(driver, this)
    }
}
