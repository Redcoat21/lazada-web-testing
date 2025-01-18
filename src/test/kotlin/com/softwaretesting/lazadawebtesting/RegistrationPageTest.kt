package com.softwaretesting.lazadawebtesting

import com.softwaretesting.helper.DriverFactory
import org.openqa.selenium.WebDriver
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

class RegistrationPageTest {
    private lateinit var driver: WebDriver
    private lateinit var registrationPage: RegistrationPage

    @BeforeClass
    fun setUpClass() {
        driver = DriverFactory.createDriver()
        driver.manage().window().maximize()
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10))

        val url = "https://lazada.co.id/#"
        driver.get(url)
        registrationPage = RegistrationPage(driver)
    }

    @AfterClass
    fun tearDownClass() {
        driver.quit()
    }

    //TODO: Implement test
    @Test
    fun todoTest() {
        TODO("Test should be implemented.")
    }
}