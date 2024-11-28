from selenium.webdriver.chrome.webdriver import WebDriver 
from selenium.webdriver.common.by import By
from selenium.webdriver.remote.webelement import WebElement
from time import sleep
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import pytest
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

"""
Fixture to ensure before every test, the registration modal is opened.
"""
@pytest.fixture(scope="function")
def registration(browser: WebDriver):
    click_on_signup_link(browser)
    yield browser

# Test Section
#TODO: Find a way to solve OTP, and reusing the phone number. Captcha is solved through extension.
"""
TC_01  Test the registration process using a valid phone number and checking the terms and conditions checkbox.
Expect: The page should change to the next page.
"""
@pytest.mark.skip(reason="This test is unsolvable for now.")
def test_register_with_valid_phone_number(registration: WebDriver) -> None:
    current_url = registration.current_url

    phone_number = "087750999479"
    type_phone_number(registration, phone_number)
    
    check_terms_and_conditions_checkbox(registration)
    
    confirm_registration_with_whatsapp(registration)
    
    # Wait for maximum 10 minutes to solve captcha. If it's completed before 10 minutes, it will continue to the next step.
    registration.implicitly_wait(600)
    
    # Wait for the page to change.
    WebDriverWait(registration, 10).until(
        EC.url_changes
    )
    
    assert False, "This test is unsolvable for now."


"""
TC_02 Test the registration process using an invalid phone number and checking the terms and conditions checkbox.
Expect: The page should not change.
"""
def test_register_with_invalid_phone_number(registration: WebDriver) -> None:
    current_url = registration.current_url

    phone_number = "241424"
    type_phone_number(registration, phone_number)
    
    check_terms_and_conditions_checkbox(registration)
    
    confirm_registration_with_whatsapp(registration)

    # Find the error toast.
    invalid_phone_number_toast: WebElement = WebDriverWait(registration, 10).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, "[class*='iweb-toast']"))
    )
    
    # Assert that the toast exist.
    assert invalid_phone_number_toast is not None, "Toast should exist"
    # Assert that the toast message is correct.
    assert invalid_phone_number_toast.text == "Please enter a valid phone number.", "Toast message should be 'Please enter a valid phone number.'"
    # Assert that we dont change page.
    assert registration.current_url == current_url
    
    sleep(1)

"""
TC_03 Test the registration process using either a valid or invalid phone number without checking the terms and conditions checkbox.
Expect: The page should not change.
"""
def test_register_with_unchecked_terms_and_conditions(registration: WebDriver) -> None:
    # Get the current url now to test that we dont change page.
    current_url = registration.current_url

    # Wait for registration modal to appear
    # Look for the phone number field inside the registration modal.
    # Wait until it appear..
    phone_number = "081331413699"
    type_phone_number(registration, phone_number)
    confirm_registration_with_whatsapp(registration)

    # Find the error toast.
    terms_and_conditions_toast: WebElement = WebDriverWait(registration, 10).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, "[class*='iweb-toast']"))
    )
    
    # Assert that the toast exist.
    assert terms_and_conditions_toast is not None, "Toast should exist"
    # Assert that the toast message is correct.
    assert terms_and_conditions_toast.text == "You should agree to our Terms of Use and Privacy Policy.", "Toast message should be 'You should agree to our Terms of Use and Privacy Policy.'"
    # Assert that we dont change page.
    assert registration.current_url == current_url, "URL should still stay the same."

    sleep(1)
