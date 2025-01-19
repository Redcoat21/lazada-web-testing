package com.softwaretesting.lazadawebtesting.facebook

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

// page_url = https://www.facebook.com/
class FacebookRegistrationPage(driver: WebDriver) {
    @FindBy(id = "email")
    lateinit var emailField: WebElement

    @FindBy(id = "pass")
    lateinit var passwordField: WebElement

    @FindBy(xpath = "//input[@type='submit']")
    lateinit var submitButton: WebElement

    /**
     * Enter email to the registration form.
     * @param email Email to be entered.
     */
    fun enterEmail(email: String) {
        emailField.sendKeys(email)
    }

    /**
     * Enter password to the registration form.
     * @param password Password to be entered.
     */
    fun enterPassword(password: String) {
        passwordField.sendKeys(password)
    }

    /**
     * Submit the registration form.
     */
    fun submitForm() {
        submitButton.click()
    }

    /**
     * Submit the registration form with the given email and password.
     * @param email Email to be submitted.
     * @param password Password to be submitted.
     */
    fun submitData(email: String, password: String) {
        enterEmail(email)
        enterPassword(password)
        submitForm()
    }

    init {
        PageFactory.initElements(driver, this)
    }
}