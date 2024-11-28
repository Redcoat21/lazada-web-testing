from selenium.webdriver.chrome.webdriver import WebDriver 
from selenium.webdriver.common.by import By
from selenium.webdriver.remote.webelement import WebElement
from time import sleep
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import os
import pytest

def click_on_login_link(browser: WebDriver):
    login_link: WebElement = browser.find_element(by=By.ID, value="anonLogin")
    login_link.click()
    
def login_with_facebook(browser: WebDriver):
    facebook_login_button: WebElement = WebDriverWait(browser, 15).until(
        EC.presence_of_element_located((By.XPATH, "//span[text()='Facebook']"))
    )
    facebook_login_button.click()
    
def fill_facebook_email(browser: WebDriver, email: str):
    email_field: WebElement = WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.ID, "email"))
    )
    email_field.send_keys(email)
    
def fill_facebook_password(browser: WebDriver, password: str):
    password_field: WebElement = WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.ID, "pass"))
    )
    password_field.send_keys(password)
    
def facebook_login_button(browser: WebDriver):
    login_button: WebElement = WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.NAME, "login"))
    )
    login_button.click()
    
def facebook_continue_as(browser: WebDriver):
    continue_as_button: WebElement = WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.XPATH, "//span[contains(normalize-space(.), 'Lanjutkan sebagai')]"))
    )
    continue_as_button.click()
    
def switch_to_facebook_window(browser: WebDriver):
    WebDriverWait(browser, 10).until(lambda d: len(d.window_handles) > 1)
    browser.switch_to.window(browser.window_handles[1])

@pytest.fixture(scope="function")
def login(browser: WebDriver):
    click_on_login_link(browser)
    return browser

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

"""
TC_012 Test the login process using an invalid Facebook account.
Expect: The page should show an error message and shouldn't change page.
"""
def test_invalid_login_with_faceboko(login: WebDriver):
    # Click on the Facebook login button
    facebook_login_button(login)

    # Switch to the Facebook login window
    switch_to_facebook_window(login)

    # Enter the email
    email = "kendrick.sam@gmail.com"
    fill_facebook_email(login, email)
    
    # Enter the password
    password = "password"
    fill_facebook_password(login, password)
    
    facebook_login_button(login)