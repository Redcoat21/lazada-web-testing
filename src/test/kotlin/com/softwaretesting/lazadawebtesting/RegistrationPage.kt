package com.softwaretesting.lazadawebtesting

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

// page_url = https://www.lazada.co.id/#?
class RegistrationPage(driver: WebDriver) {
    // Gaada cara lain selain xpath panjang :D
    @FindBy(xpath = "(//div[@class='iweb-input-container']//input[@type='text' and @placeholder='Enter your phone number'])[last()]")
    lateinit var phoneNumberField: WebElement

    @FindBy(css = "div[class$='iweb-checkbox-icon--box']")
    lateinit var termsAndConditionsCheckbox: WebElement

    @FindBy(css = "button[class*='iweb-button-primary'] div[class='iweb-button-mask']")
    lateinit var whatsappOtpButton: WebElement

    @FindBy(css = "button[class*='iweb-button-secondary'] div[class='iweb-button-mask']")
    lateinit var smsOtpButton: WebElement

    init {
        PageFactory.initElements(driver, this)
    }
}
