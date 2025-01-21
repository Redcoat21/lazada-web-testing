package com.softwaretesting.lazadawebtesting.cart

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

// page_url = https://cart.lazada.co.id
class CartPage(driver: WebDriver) {
    @FindBy(css = ".next-checkbox-inner")
    lateinit var selectAllCheckbox: List<WebElement>

    /**
     * Check checkbox in the cart page.
     * @param index index should be greater than 0, since 0 is the first checkbox which check all checkbox.
     */
    fun checkCheckbox(index: Int) {
        selectAllCheckbox[index].click()
    }

    /**
     * Check all checkbox in the cart page.
     */
    fun checkCheckbox() {
        selectAllCheckbox.firstOrNull()?.click()
    }

    init {
        PageFactory.initElements(driver, this)
    }
}