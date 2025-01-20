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

    @FindBy(id = "q")
    lateinit var searchInput: WebElement

    /**
     * Search for an item based on the given keyword.
     * @param keyword The keyword to search for.
     */
    fun searchItem(keyword: String) {
        enterSearchKeyword(keyword)
        searchInput.submit()
    }

    /**
     * Enter the search keyword in the search input.
     * @param keyword The keyword to search for.
     */
    fun enterSearchKeyword(keyword: String) {
        searchInput.sendKeys(keyword)
    }

    init {
        PageFactory.initElements(driver, this)
    }
}
