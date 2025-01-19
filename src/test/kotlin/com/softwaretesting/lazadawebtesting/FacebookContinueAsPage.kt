package com.softwaretesting.lazadawebtesting

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import java.util.List

// page_url = https://www.facebook.com/privacy/consent
class FacebookContinueAsPage(driver: WebDriver) {
    @FindBy(xpath = "//div[@role='button']")
    lateinit var continueAsButton: WebElement

    /**
     * Finishing the facebook login / sign up process.
     */
    fun clickContinueAsButton() {
        continueAsButton.click()
    }

    init {
        PageFactory.initElements(driver, this)
    }
}