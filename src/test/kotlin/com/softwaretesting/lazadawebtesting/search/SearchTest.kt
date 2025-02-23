package com.softwaretesting.lazadawebtesting.search

import com.softwaretesting.helper.DriverFactory
import com.softwaretesting.lazadawebtesting.MainPage
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.Assert
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import java.time.Duration

class SearchTest {
    private lateinit var driver: WebDriver
    private lateinit var mainPage: MainPage
    private lateinit var dotenv: Dotenv
    private lateinit var duration: Duration
    private lateinit var wait: WebDriverWait

    @BeforeClass
    fun setupClass() {
        driver = DriverFactory.createDriver()
        driver.manage().window().maximize()
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))

        duration = Duration.ofSeconds(10)
        wait = WebDriverWait(driver, duration)

        val url = "https://lazada.co.id/#"
        driver.get(url)
        mainPage = MainPage(driver)

        dotenv = dotenv()
    }

    @AfterClass
    fun teardownClass() {
        driver.quit()
    }

    /**
     * TC_14 Search for a valid item. (Item that exists)
     */
    @Test
    fun searchValidItem() {
        val keyword = "Laptop"
        mainPage.searchItem(keyword)

        wait.until(ExpectedConditions.urlContains("q=$keyword"))
        val productCards = driver.findElements(By.xpath("//div[@class='Bm3ON']//a[@title]"))
        Assert.assertListContains(productCards, { it.getAttribute("title")?.contains(keyword) ?: false }, "Product cards should contain the keyword.")
    }

    /**
     * TC_15 Search for an invalid item. (Item that does not exist)
     */
    @Test
    fun searchInvalidItem() {
        val keyword = "XXXXXX"
        mainPage.searchItem(keyword)

        wait.until(ExpectedConditions.urlContains("q=$keyword"))
        val productCards = driver.findElements(By.xpath("//div[@class='Bm3ON']//a[@title]"))
        Assert.assertTrue(productCards.isEmpty(), "Product cards should be empty.")
    }

    /**
     * TC_16 Search Suggestion appear for the given keyword.
     */
    @Test
    fun searchSuggestionAppear() {
        val keyword = "Phone"
        mainPage.enterSearchKeyword(keyword)

        val searchSuggestions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div[class*=\"suggest-item-content\"]")))

        Assert.assertTrue(searchSuggestions.isNotEmpty(), "Search suggestion should appear.")
    }
}