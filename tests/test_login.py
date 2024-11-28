from selenium.webdriver.chrome.webdriver import WebDriver 
from selenium.webdriver.common.by import By
from selenium.webdriver.remote.webelement import WebElement
from time import sleep
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import os

def test_login_with_facebook(browser: WebDriver):
    # Navigate to the login page
    print("Navigating to Lazada homepage...")
    browser.get("https://www.lazada.co.id/")
    sleep(3)  # Slow down the process

    # Wait for the login button to appear and click it
    print("Waiting for the login button to appear...")
    login_button: WebElement = WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.XPATH, '//*[@id="anonLogin"]/a'))
    )
    print("Clicking the login button...")
    login_button.click()
    sleep(3)  # Slow down the process

    # Wait for the Facebook login button to appear and click it
    print("Waiting for the Facebook login button to appear...")
    facebook_login_button: WebElement = WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.XPATH, "//span[text()='Facebook']"))
    )
    print("Clicking the Facebook login button...")
    facebook_login_button.click()
    sleep(3)  # Slow down the process

    # Switch to the Facebook login window
    print("Switching to the Facebook login window...")
    WebDriverWait(browser, 10).until(lambda d: len(d.window_handles) > 1)
    browser.switch_to.window(browser.window_handles[1])
    sleep(3)  # Slow down the process

    # Enter the email
    print("Entering the email...")
    email_field: WebElement = WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.ID, "email"))
    )
    email_field.send_keys(os.getenv("FACEBOOK_EMAIL"))
    sleep(3)  # Slow down the process

    # Enter the password
    print("Entering the password...")
    password_field: WebElement = WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.ID, "pass"))
    )
    password_field.send_keys(os.getenv("FACEBOOK_PASSWORD"))
    sleep(3)  # Slow down the process

    # Click the login button
    print("Clicking the login button...")
    login_button: WebElement = WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.NAME, "login"))
    )
    login_button.click()
    sleep(3)  # Slow down the process

    # Wait for the account selection to appear and select the registered account
        # Wait for the "Lanjutkan sebagai" button to appear and click it
    print("Waiting for the 'Lanjutkan sebagai' button to appear...")
    lanjutkan_button: WebElement = WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.XPATH, "//span[contains(normalize-space(.), 'Lanjutkan sebagai')]"))
    )
    print("Clicking the 'Lanjutkan sebagai' button...")
    lanjutkan_button.click()
    sleep(3)  # Slow down the process

    # //span[contains(normalize-space(.), 'Lanjutkan sebagai')]
    # account_selection: WebElement = WebDriverWait(browser, 10).until(
    #     EC.presence_of_element_located((By.XPATH, f"//div[contains(text(), '{os.getenv("FACEBOOK_EMAIL")}')]"))
    # )
    print("Selecting the registered account...")
    # account_selection.click()
    sleep(3)  # Slow down the process

    # Switch back to the Lazada window
    print("Switching back to the Lazada window...")
    browser.switch_to.window(browser.window_handles[0])
    sleep(3)  # Slow down the process

    # Wait for the homepage to load and verify the login

    # BUG: Kayaknya yang ini problematik, soalnya text Welcome back ga bakal muncul.
    print("Waiting for the homepage to load...")
    WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.XPATH, "//div[contains(text(), 'Welcome back')]"))
    )

    # Assert that the user is on the homepage
    print("Verifying the login...")
    assert "https://www.lazada.co.id/" in browser.current_url, "User should be redirected to the homepage"
    print("Test completed successfully.")