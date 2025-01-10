package com.softwaretesting.lazadawebtesting

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

// page_url = https://www.jetbrains.com/
class MainPage(driver: WebDriver) {
    @FindBy(xpath = "//*[@data-test-marker='Developer Tools']")
    lateinit var seeDeveloperToolsButton: WebElement

    @FindBy(xpath = "//*[@data-test='suggestion-action']")
    lateinit var findYourToolsButton: WebElement

    @FindBy(xpath = "//div[@data-test='main-menu-item' and @data-test-marker = 'Developer Tools']")
    lateinit var toolsMenu: WebElement

    @FindBy(css = "[data-test='site-header-search-action']")
    lateinit var searchButton: WebElement

    init {
        PageFactory.initElements(driver, this)
    }
}
