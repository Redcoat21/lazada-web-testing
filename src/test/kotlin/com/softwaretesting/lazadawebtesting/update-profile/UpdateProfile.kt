package com.softwaretesting.lazadawebtesting.update_profile

import com.softwaretesting.helper.DriverFactory
import com.softwaretesting.helper.LoginHelper
import com.softwaretesting.helper.LoginMethod
import com.softwaretesting.lazadawebtesting.MainPage
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.Assert
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import java.time.Duration

class UpdateProfileTest {
    private lateinit var driver: WebDriver
    private lateinit var mainPage: MainPage
    private lateinit var dotenv: Dotenv
    private lateinit var duration: Duration
    private lateinit var wait: WebDriverWait

    @BeforeMethod
    fun setupMethod() {
        driver = DriverFactory.createDriver()
        driver.manage().window().maximize()
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))

        duration = Duration.ofSeconds(10)

        val url = "https://lazada.co.id/#"
        driver.get(url)

        wait = WebDriverWait(driver, duration)

        // Login
        MainPage(driver).loginButton.click()
        LoginHelper.login(LoginMethod.PASSWORD, driver, true)

        Thread.sleep(5000)
    }

    @AfterMethod
    fun teardownMethod() {
        driver.quit()
    }

//    @BeforeMethod
//    fun setUpMethod() {
//        val url = "https://lazada.co.id/#"
//        driver.get(url)
//
//        MainPage(driver).loginButton.click()
//        LoginHelper.login(LoginMethod.PASSWORD, driver, true)
//        Thread.sleep(5000)
//    }

    /**
     * TC_21 Update Profile
     */
    @Test
    fun updateProfile() {
        // 1. Access User Account Dropdown Menu
        val accountDropdown = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='topActionUserAccont']"))
        )
        accountDropdown.click()
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lzdMyAccountPop")))

        // 2. Navigate to Profile Settings Page
        val panelAkun = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='lzdMyAccountPop']//a[@id='account-popup-manage-account']")
            )
        )
        panelAkun.click()

        val ubahLink = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@data-spm='dprofile_edit' and contains(text(), 'UBAH')]")            )
        )

        ubahLink.click()

        val ubahProfilLink = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(text(), 'Ubah Profil')]")
            )
        )

        // Get the text of the "Ubah Profil" link
        val actualText = ubahProfilLink.text

        // Expected text
        val expectedText = "Ubah Profil"

        // Assert that the actual text matches the expected text
        Assert.assertEquals( actualText, expectedText,"The text of the link does not match the expected value.")

        // Modify Name
        val inputField = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@placeholder='Nama Lengkap']")
            )
        )

        // Clear the current value of the input field
        val currentValue = inputField.getAttribute("value")

        // Send backspace keys to clear the input field
        for (i in currentValue.indices) {
            inputField.sendKeys(Keys.BACK_SPACE)
        }

        val newName = "haha"
        // Enter the new value "baru" into the input field
        inputField.sendKeys(newName)

        // 7. Submit Profile Changes
        val saveButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='SIMPAN PERUBAHAN']")
            )
        )
        saveButton.click()

        // Verification: Wait for the page to reload and verify the updated values
        Thread.sleep(5000) // Wait for the page to reload

        val profileInfoDiv = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div.my-profile-item-info")
            )
        )

        // Get the text value of the <div> element
        val textValue = profileInfoDiv.text

        Assert.assertEquals(newName, textValue, "Error! it should be $newName")
    }
}
