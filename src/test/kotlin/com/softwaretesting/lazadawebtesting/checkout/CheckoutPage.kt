package com.softwaretesting.lazadawebtesting.checkout

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import java.util.List

// page_url = https://www.lazada.co.id/#?
class CheckoutPage(driver: WebDriver) {
    init {
        PageFactory.initElements(driver, this)
    }
}