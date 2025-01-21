package com.softwaretesting.helper

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class CartHelper {
    companion object {
        fun addToCart(driver: WebDriver) {
            val duration = java.time.Duration.ofSeconds(10)
            val wait = WebDriverWait(driver, duration)

            // Find and click the first flash sale product
            val firstProduct = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class, 'flash-sale-comp-container')]//div[@class='card-fs-content-body']//a[1]")
                )
            )
            firstProduct.click()

            // Wait for the product page to load completely
            wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='module_add_to_cart']"))
            )

            // Click add to cart button
            val addToCartButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@id='module_add_to_cart']//button[contains(@class, 'add-to-cart-buy-now-btn') and contains(@class, 'pdp-button_theme_orange')]")
                )
            )
            addToCartButton.click()
        }
    }
}