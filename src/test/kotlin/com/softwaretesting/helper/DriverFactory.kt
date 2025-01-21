package com.softwaretesting.helper

import io.github.bonigarcia.wdm.WebDriverManager
import io.github.cdimascio.dotenv.dotenv
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions

/**
 * A factory class to create a WebDriver instance.
 */
class DriverFactory {
    /**
     * The browser type enum. Is used to determine which WebDriver to use.
     */
    private enum class BrowserType {
        CHROME,
        FIREFOX,
        EDGE
    }

    companion object {
        /**
         * Create a WebDriver instance based on the .env file.
         */
        fun createDriver(): WebDriver {
            val dotenv = dotenv()
            val browserType = getBrowserType(dotenv.get("BROWSER_TYPE"))
            val driverSetUp = dotenv.get("SETUP_DRIVER")?.toBoolean()!!
            if(driverSetUp) {
                setupDriver(browserType)
            }
            return getWebDriver(browserType)
        }

        /**
         * Convert the browser type string to the BrowserType enum.
         * @param browserTypeString the browser type string to convert. Should have been retrieved from the .env file.
         * @return the BrowserType enum.
         */
        private fun getBrowserType(browserTypeString: String): BrowserType {
            return when (browserTypeString.uppercase()) {
                "CHROME" -> BrowserType.CHROME
                "FIREFOX" -> BrowserType.FIREFOX
                "EDGE" -> BrowserType.EDGE
                else -> throw IllegalArgumentException("Browser type $browserTypeString is not supported")
            }
        }

        /**
         * Set up the driver for the chosen browser, using WebDriverManager
         * @param browserType the browser type to use. (i.e. Chrome, Firefox, or Edge)
         * @return the WebDriver for the chosen browser.
         */
        private fun setupDriver(browserType: BrowserType) {
            when (browserType) {
                BrowserType.CHROME -> WebDriverManager.chromedriver().setup()
                BrowserType.FIREFOX -> WebDriverManager.firefoxdriver().setup()
                BrowserType.EDGE -> WebDriverManager.edgedriver().setup()
            }
        }

        /**
         * Get the driver for the chosen browser.
         * @param browserType the browser type to use. (i.e. Chrome, Firefox, or Edge)
         * @return the WebDriver for the chosen browser.
         */
        private fun getWebDriver(browserType: BrowserType): WebDriver {
            return when (browserType) {
                BrowserType.CHROME -> ChromeDriver(getChromeOptions())
                BrowserType.FIREFOX -> FirefoxDriver(getFirefoxOptions())
                BrowserType.EDGE -> EdgeDriver(getEdgeOptions())
            }
        }

        /**
         * Get the ChromeOptions for the ChromeDriver.
         * @return the ChromeOptions for the ChromeDriver.
         */
        private fun getChromeOptions(): ChromeOptions {
            return ChromeOptions().apply {
                addArguments("--disable-blink-features=AutomationControlled")
                setExperimentalOption("excludeSwitches", arrayOf("enable-automation"))
                setExperimentalOption("useAutomationExtension", false)
            }
        }

        /**
         * Get the FirefoxOptions for the FirefoxDriver.
         * @return the FirefoxOptions for the FirefoxDriver.
         */
        private fun getFirefoxOptions(): FirefoxOptions {
            return FirefoxOptions().apply {
                addPreference("dom.webdriver.enabled", false)
                addPreference("useAutomationExtension", false)
                addPreference("general.useragent.override", "your_custom_user_agent")
            }
        }

        /**
         * Get the EdgeOptions for the EdgeDriver.
         * @return the EdgeOptions for the EdgeDriver.
         */
        private fun getEdgeOptions(): EdgeOptions {
            return EdgeOptions().apply {
                addArguments("--disable-blink-features=AutomationControlled")
                setExperimentalOption("excludeSwitches", arrayOf("enable-automation"))
                setExperimentalOption("useAutomationExtension", false)
            }
        }
    }
}