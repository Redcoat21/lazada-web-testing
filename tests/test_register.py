from selenium.webdriver.chrome.webdriver import WebDriver 
from selenium.webdriver.common.by import By
from selenium.webdriver.remote.webelement import WebElement
from time import sleep
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from conftest import browser as BrowserConfig
# Helper Function Section

"""
Click on the signup link to pop up the registration modal.
"""
def click_on_signup_link(browser: WebDriver):
    register_link: WebElement = browser.find_element(by=By.ID, value="anonSignup")
    register_link.click()

"""
Type the phone number into the phone number field.
"""
def type_phone_number(browser: WebDriver, phone_number: str):
    phone_number_field: WebElement = WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.XPATH, "//input[@class='iweb-input' and @placeholder='Enter your phone number']"))
    )
    phone_number_field.send_keys(phone_number)
    
"""
Confirm the registration by clicking on the send by whatsapp button.
"""
def confirm_registration_with_whatsapp(browser: WebDriver):
    send_by_whatsapp_button: WebElement = WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.XPATH, "//button[.//div[contains(text(), 'Kirim kode via Whatsapp')]]"))
    )
    
    send_by_whatsapp_button.click()

"""
Check the terms and conditions checkbox.
"""
def check_terms_and_conditions_checkbox(browser: WebDriver):
    terms_and_conditions_checkbox = browser.find_element(by=By.XPATH, value="//label[contains(@class, 'iweb-checkbox')]")
    terms_and_conditions_checkbox.click()

# Test Section
"""
Test the registration process using an invalid phone number and checking the terms and conditions checkbox.
"""
def test_register_with_invalid_phone_number(browser: WebDriver) -> None:
    current_url = browser.current_url

    click_on_signup_link(browser)

    phone_number = "241424"
    type_phone_number(browser, phone_number)
    
    check_terms_and_conditions_checkbox(browser)
    
    confirm_registration_with_whatsapp(browser)

    # Find the error toast.
    invalid_phone_number_toast: WebElement = WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, "[class*='iweb-toast']"))
    )
    
    # Assert that the toast exist.
    assert invalid_phone_number_toast is not None, "Toast should exist"
    # Assert that the toast message is correct.
    assert invalid_phone_number_toast.text == "Please enter a valid phone number.", "Toast message should be 'Please enter a valid phone number.'"
    # Assert that we dont change page.
    assert browser.current_url == current_url
    
    sleep(1)

"""
Test the registration process using either a valid or invalid phone number without checking the terms and conditions checkbox.
"""
def test_register_with_unchecked_terms_and_conditions(browser: WebDriver) -> None:
    # Get the current url now to test that we dont change page.
    current_url = browser.current_url

    # Make the registration modal pop up.
    click_on_signup_link(browser)
    # Wait for registration modal to appear
    # Look for the phone number field inside the registration modal.
    # Wait until it appear..
    phone_number = "081331413699"
    type_phone_number(browser, phone_number)
    confirm_registration_with_whatsapp(browser)

    # Find the error toast.
    terms_and_conditions_toast: WebElement = WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, "[class*='iweb-toast']"))
    )
    
    # Assert that the toast exist.
    assert terms_and_conditions_toast is not None, "Toast should exist"
    # Assert that the toast message is correct.
    assert terms_and_conditions_toast.text == "You should agree to our Terms of Use and Privacy Policy.", "Toast message should be 'You should agree to our Terms of Use and Privacy Policy.'"
    # Assert that we dont change page.
    assert browser.current_url == current_url, "URL should still stay the same."

    sleep(1)