from selenium.webdriver.chrome.webdriver import WebDriver
from selenium.webdriver.common.by import By
from selenium.webdriver.remote.webelement import WebElement
from time import sleep
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import pytest


# Test Section
class TestRegister:
    browser: WebDriver

    @pytest.fixture(scope="function", autouse=True)
    def _registration(self, browser: WebDriver):
        """
        Fixture to ensure before every test, the registration modal is opened.
        """
        self.browser = browser
        self.click_on_signup_link()

    def check_terms_and_conditions_checkbox(self):
        """
        Check the terms and conditions checkbox.
        """
        terms_and_conditions_checkbox = self.browser.find_element(
            by=By.XPATH, value="//label[contains(@class, 'iweb-checkbox')]"
        )
        terms_and_conditions_checkbox.click()

    def confirm_registration_with_whatsapp(self):
        """
        Confirm the registration by clicking on the send by whatsapp button.
        """
        send_by_whatsapp_button: WebElement = WebDriverWait(self.browser, 10).until(
            EC.presence_of_element_located(
                (
                    By.XPATH,
                    "//button[.//div[contains(text(), 'Kirim kode via Whatsapp')]]",
                )
            )
        )

        send_by_whatsapp_button.click()

    def click_on_signup_link(self):
        """
        Click on the signup link to pop up the registration modal.
        """
        register_link: WebElement = self.browser.find_element(
            by=By.ID, value="anonSignup"
        )
        register_link.click()

    def type_phone_number(self, phone_number: str):
        """
        Type the phone number into the phone number field.
        """
        phone_number_field: WebElement = WebDriverWait(self.browser, 10).until(
            EC.presence_of_element_located(
                (
                    By.XPATH,
                    "//input[@class='iweb-input' and @placeholder='Enter your phone number']",
                )
            )
        )
        phone_number_field.send_keys(phone_number)

    @pytest.mark.skip(reason="This test is unsolvable for now.")
    def test_register_with_valid_phone_number(self) -> None:
        # TODO: Find a way to solve OTP, and reusing the phone number. Captcha is solved through extension.
        """
        TC_01  Test the registration process using a valid phone number and checking the terms and conditions checkbox.
        Expect: The page should change to the next page.
        """
        current_url = self.browser.current_url

        phone_number = "087750999479"
        self.type_phone_number(phone_number)

        self.check_terms_and_conditions_checkbox()

        self.confirm_registration_with_whatsapp()

        # Wait for maximum 10 minutes to solve captcha. If it's completed before 10 minutes, it will continue to the next step.
        self.browser.implicitly_wait(600)

        # Wait for the page to change.
        # We don't know what's the url going to be, so just use a placeholder for now.
        expected_url = "https://example.com"
        WebDriverWait(self.browser, 10).until(EC.url_changes(expected_url))

        assert False, "This test is unsolvable for now."

    def test_register_with_invalid_phone_number(self) -> None:
        """
        TC_02 Test the registration process using an invalid phone number and checking the terms and conditions checkbox.
        Expect: The page should not change.
        """
        current_url = self.browser.current_url

        phone_number = "241424"
        self.type_phone_number(phone_number)

        self.check_terms_and_conditions_checkbox()

        self.confirm_registration_with_whatsapp()

        # Find the error toast.
        invalid_phone_number_toast: WebElement = WebDriverWait(self.browser, 10).until(
            EC.presence_of_element_located((By.CSS_SELECTOR, "[class*='iweb-toast']"))
        )

        # Assert that the toast exist.
        assert invalid_phone_number_toast is not None, "Toast should exist"
        # Assert that the toast message is correct.
        assert (
            invalid_phone_number_toast.text == "Please enter a valid phone number."
        ), "Toast message should be 'Please enter a valid phone number.'"
        # Assert that we don't change page.
        assert self.browser.current_url == current_url

        sleep(1)

    def test_register_with_unchecked_terms_and_conditions(self) -> None:
        """
        TC_03 Test the registration process using either a valid or invalid phone number without checking the terms and conditions checkbox.
        Expect: The page should not change.
        """
        # Get the current url now to test that we don't change page.
        current_url = self.browser.current_url

        # Wait for registration modal to appear
        # Look for the phone number field inside the registration modal.
        # Wait until it appear.
        phone_number = "081331413699"
        self.type_phone_number(phone_number)
        self.confirm_registration_with_whatsapp()

        # Find the error toast.
        terms_and_conditions_toast: WebElement = WebDriverWait(self.browser, 10).until(
            EC.presence_of_element_located((By.CSS_SELECTOR, "[class*='iweb-toast']"))
        )

        # Assert that the toast exist.
        assert terms_and_conditions_toast is not None, "Toast should exist"
        # Assert that the toast message is correct.
        assert (
            terms_and_conditions_toast.text
            == "You should agree to our Terms of Use and Privacy Policy."
        ), "Toast message should be 'You should agree to our Terms of Use and Privacy Policy.'"
        # Assert that we don't change page.
        assert (
            self.browser.current_url == current_url
        ), "URL should still stay the same."

        sleep(1)
