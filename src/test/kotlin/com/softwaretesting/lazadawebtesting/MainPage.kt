package com.softwaretesting.lazadawebtesting

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

// page_url = https://www.lazada.co.id/#?
class MainPage(driver: WebDriver) {
    @FindBy(id = "anonSignup")
    lateinit var signUpButton: WebElement

    @FindBy(id = "anonLogin")
    lateinit var loginButton: WebElement

    init {
        PageFactory.initElements(driver, this)
    }
}
