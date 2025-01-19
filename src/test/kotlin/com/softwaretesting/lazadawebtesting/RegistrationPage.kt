package com.softwaretesting.lazadawebtesting

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

    @FindBy(xpath = "//div[@class='iweb-passcode-input-cell-container']//div[@class='iweb-passcode-input-cell']")
    lateinit var otpInputsContainer: List<WebElement>

    @FindBy(xpath = "(//button[@class='iweb-button iweb-button-primary'])[last()]")
    lateinit var confirmOtpButton: WebElement

    /**
     * Enter phone number to the registration form.
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
     */
    fun getOtp(otpMethod: OtpMethod) {
        when (otpMethod) {
            OtpMethod.WHATSAPP -> whatsappOtpButton.click()
            OtpMethod.SMS -> smsOtpButton.click()
        }
    }

    init {
        PageFactory.initElements(driver, this)
    }
}
