package com.softwaretesting.helper

import io.github.cdimascio.dotenv.dotenv
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

/**
 * Helper class to do log in, Note that this class assumes that the login modal is displayed.
 */
class LoginHelper {
    companion object {
        /**
         * Login using the chosen method.
         * @param loginMethod The login method to use.
         * @param driver The WebDriver instance.
         * @throws IllegalArgumentException If the login method is invalid.
         */
        fun login(loginMethod: LoginMethod, driver: WebDriver) {
            when(loginMethod) {
                LoginMethod.PASSWORD -> loginUsingPhoneNumberAndPassword(driver)
                LoginMethod.FACEBOOK -> loginUsingFacebook(driver)
                else -> throw IllegalArgumentException("Invalid login method")
            }
        }

        /**
         * Login using phone number and OTP.
         * @param driver The WebDriver instance.
         */
        private fun loginUsingPhoneNumberAndPassword(driver: WebDriver) {
            val inputs = driver.findElements(By.cssSelector(".iweb-input"))

            val (phoneNumberInput, passwordInput) = inputs
            val phoneNumber = dotenv().get("LAZADA_VALID_LOGIN_PHONE_NUMBER")
            val password = dotenv().get("LAZADA_VALID_LOGIN_PASSWORD")

            phoneNumberInput.sendKeys(phoneNumber)
            passwordInput.sendKeys(password)

            val submitButton = driver.findElement(By.cssSelector(".iweb-button-mask"))
            submitButton.click()
        }

        /**
         * Login using facebook.
         */
        private fun loginUsingFacebook(driver: WebDriver) {
            TODO("Not yet implemented")
        }
    }
}