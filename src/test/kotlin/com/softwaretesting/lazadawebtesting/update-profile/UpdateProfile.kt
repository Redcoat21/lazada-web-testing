package com.softwaretesting.lazadawebtesting.update_profile

import com.softwaretesting.helper.DriverFactory
import com.softwaretesting.helper.LoginHelper
import com.softwaretesting.helper.LoginMethod
import com.softwaretesting.lazadawebtesting.MainPage
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.openqa.selenium.By
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
        wait = WebDriverWait(driver, duration)

        val url = "https://lazada.co.id/#"
        driver.get(url)
        mainPage = MainPage(driver)

        dotenv = dotenv()
    }

    @AfterMethod
    fun teardownMethod() {
        driver.quit()
    }

    @BeforeMethod
    fun setUpMethod() {
        val url = "https://lazada.co.id/#"
        driver.get(url)

        MainPage(driver).loginButton.click()
        LoginHelper.login(LoginMethod.PASSWORD, driver, true)
        Thread.sleep(5000)
    }

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
        wait.until(ExpectedConditions.urlContains("//member.lazada.co.id/user/account#"))
        Assert.assertEquals(driver.title, "Atur Akun Saya", "Page title should be 'Atur Akun Saya'")

        // 3. Initiate Profile Editing
        val editProfileButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='dashboard-profile']//a[@data-spm='dprofile_edit']")
            )
        )
        editProfileButton.click()
        wait.until(ExpectedConditions.urlContains("//member.lazada.co.id/user/profile#/profile/edit"))

        // 4. Modify Name
        val nameInput = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@data-meta='Field' and @type='text' and contains(@class, 'edit-profile-input-name')]")
            )
        )
        nameInput.clear()
        val updatedName = "Updated User Name"
        nameInput.sendKeys(updatedName)
        Assert.assertEquals(nameInput.getAttribute("value"), updatedName, "Name input should contain the new name")

        // 5. Modify Birthdate
        val monthSelect = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//span[@data-meta='Field' and @id='month' and contains(@class, 'mod-birthday-month')]")
            )
        )
        monthSelect.click()
        val monthOption = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='next-select-menu-content']//li[text()='Januari']")
            )
        )
        monthOption.click()

        val daySelect = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//span[@data-meta='Field' and @id='day' and contains(@class, 'mod-birthday-day')]")
            )
        )
        daySelect.click()
        val dayOption = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='next-select-menu-content']//li[text()='1']")
            )
        )
        dayOption.click()

        val yearSelect = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//span[@data-meta='Field' and @id='year' and contains(@class, 'mod-birthday-year')]")
            )
        )
        yearSelect.click()
        val yearOption = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='next-select-menu-content']//li[text()='1999']")
            )
        )
        yearOption.click()

        // 6. Modify Gender
        val genderSelect = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//span[@data-meta='Field' and @id='gender' and contains(@class, 'mod-gender-gender')]")
            )
        )
        genderSelect.click()
        val genderOption = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='next-select-menu-content']//li[text()='Pria']")
            )
        )
        genderOption.click()

        // 7. Submit Profile Changes
        val saveButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='SIMPAN PERUBAHAN']")
            )
        )
        saveButton.click()

        // Verification: Wait for the page to reload and verify the updated values
        Thread.sleep(5000) // Wait for the page to reload

        // Assertion Step 1 & 2 : Locate elements and get values
        val nameElement = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='my-profile-item'][.//h3[text()='Nama lengkap']]//div[@class='my-profile-item-info']")
            )
        )
        val displayedName = nameElement.text

        val birthdateElement = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='my-profile-item'][.//h3[text()='Tanggal lahir']]//div[@class='my-profile-item-info']")
            )
        )
        val displayedBirthDate = birthdateElement.text

        val genderElement = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='my-profile-item'][.//h3[text()='Jenis kelamin']]//div[@class='my-profile-item-info']")
            )
        )
        val displayedGender = genderElement.text

        // Assertion Step 3: Compare displayed text with expected input data and logging
        Assert.assertEquals(displayedName, updatedName, "Name does not match the updated value")
        println("Expected name: $updatedName, actual: $displayedName")

        Assert.assertEquals(displayedBirthDate.trim(), "Januari 1, 1999", "Birthdate does not match the updated value")
        println("Expected birthdate: Januari 1, 1999, actual: $displayedBirthDate")

        Assert.assertEquals(displayedGender.trim(), "Pria", "Gender does not match the updated value")
        println("Expected gender: Pria, actual: $displayedGender")
    }
}
