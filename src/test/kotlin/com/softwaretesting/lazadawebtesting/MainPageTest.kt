package com.softwaretesting.lazadawebtesting

import io.github.bonigarcia.wdm.WebDriverManager
import org.testng.annotations.*
import org.testng.Assert.*
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import java.time.Duration

class MainPageTest {
    private lateinit var driver: WebDriver
    private lateinit var mainPage: MainPage

    @BeforeMethod
    fun setUp() {
        driver = WebDriverManager.firefoxdriver().create()
        driver.manage().window().maximize()
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
        driver.get("https://www.jetbrains.com/")

        mainPage = MainPage(driver)
    }

    @AfterMethod
    fun tearDown() {
        driver.quit()
    }

    @Test
    fun search() {
        mainPage.searchButton.click()

        val searchField = driver.findElement(By.cssSelector("[data-test='search-input']"))
        searchField.sendKeys("Selenium")
        val submitButton = driver.findElement(By.cssSelector("button[data-test='full-search-button']"))
        submitButton.click()

        val searchPageField = driver.findElement(By.cssSelector("input[data-test='search-input']"))
        assertEquals(searchPageField.getAttribute("value"), "Selenium")
    }

    @Test
    fun toolsMenu() {
        mainPage.toolsMenu.click()

        val menuPopup = driver.findElement(By.cssSelector("div[data-test='main-submenu']"))
        assertTrue(menuPopup.isDisplayed)
    }

    @Test
    fun navigationToAllTools() {
        mainPage.seeDeveloperToolsButton.click()
        mainPage.findYourToolsButton.click()

        val productsList = driver.findElement(By.id("products-page"))
        assertTrue(productsList.isDisplayed)
        assertEquals(driver.title, "All Developer Tools and Products by JetBrains")
    }
}
