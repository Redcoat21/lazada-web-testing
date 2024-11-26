from selenium.webdriver.chrome.webdriver import WebDriver 
from selenium.webdriver.common.by import By
from selenium.webdriver.remote.webelement import WebElement
from time import sleep
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

def test_register_with_unchecked_terms_and_conditions(browser: WebDriver):
    current_url = browser.current_url

    register_link: WebElement = browser.find_element(by=By.XPATH, value="//*[@id='anonSignup']/a")
    register_link.click()
    # Wait for registration modal to appear
    # Look for the phone number field inside the registration modal.
    # Wait until it appear..
    phone_number_field: WebElement = WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.XPATH, "//input[@class='iweb-input' and @placeholder='Enter your phone number']"))
    )
    
    phone_number = "081331413699"
    phone_number_field.send_keys(phone_number)
    
    send_by_whatsapp_button: WebElement = WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.XPATH, "//button[.//div[contains(text(), 'Kirim kode via Whatsapp')]]"))
    )
    
    send_by_whatsapp_button.click()

    terms_and_conditions_toast: WebElement = WebDriverWait(browser, 10).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, "[class*='iweb-toast']"))
    )
    
    # Assert that the toast exist.
    assert terms_and_conditions_toast is not None, "Toast should exist"
    # Assert that the toast message is correct.
    assert terms_and_conditions_toast.text == "You should agree to our Terms of Use and Privacy Policy.", "Toast message should be 'You should agree to our Terms of Use and Privacy Policy.'"
    # Assert that we dont change page.
    assert browser.current_url == current_url, "URL should still stay the same."

    sleep(3)