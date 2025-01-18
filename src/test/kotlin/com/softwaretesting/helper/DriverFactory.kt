package com.softwaretesting.helper

import io.github.bonigarcia.wdm.WebDriverManager
import io.github.cdimascio.dotenv.dotenv
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver

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
            val useLocalBrowser = dotenv.get("USE_LOCAL_BROWSER").toBoolean()
            val browserType = getBrowserType(dotenv.get("BROWSER_TYPE"))

            return getWebDriver(useLocalBrowser, browserType)
        }

        /**
         * Convert the browser type string to the BrowserType enum.
         * @param browserTypeString the browser type string to convert. Should have been retrieved from the .env file.
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
         * Get the driver for the chosen browser using WebDriverManager, and it will use the locally installed browser.
         * @param browserType the browser type to use. (i.e. Chrome, Firefox, or Edge)
         */
        private fun getLocalBrowser(browserType: BrowserType): WebDriver {
            return when (browserType) {
                BrowserType.CHROME -> WebDriverManager.chromedriver().create()
                BrowserType.FIREFOX -> WebDriverManager.firefoxdriver().create()
                BrowserType.EDGE -> WebDriverManager.edgedriver().create()
            }
        }

        /**
         * Get the driver for the chosen browser using Selenium's WebDriver, and it will use either the locally installed browser or the remote browser using Selenoid.
         * @param browserType the browser type to use. (i.e. Chrome, Firefox, or Edge)
         */
        private fun getRemoteBrowser(browserType: BrowserType): WebDriver {
            return when (browserType) {
                BrowserType.CHROME -> ChromeDriver()
                BrowserType.FIREFOX -> FirefoxDriver()
                BrowserType.EDGE -> EdgeDriver()
            }
        }

        /**
         * Get the driver for the chosen browser.
         * @param useLocalBrowser whether to use the locally installed browser or the remote browser using Selenoid.
         * @param browserType the browser type to use. (i.e. Chrome, Firefox, or Edge)
         */
        private fun getWebDriver(useLocalBrowser: Boolean, browserType: BrowserType): WebDriver {
            return if (useLocalBrowser) {
                getLocalBrowser(browserType)
            } else {
                getRemoteBrowser(browserType)
            }
        }
    }
}