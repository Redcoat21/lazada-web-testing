from time import sleep

from selenium.webdriver.common.by import By
from selenium.webdriver.ie.webdriver import WebDriver
from selenium.webdriver.remote.webelement import WebElement
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


class FacebookHelper:
    FACEBOOK_WINDOW = 1
    LAZADA_WINDOW = 0

    def __init__(self, browser: WebDriver):
        self.browser = browser

    def click_on_facebook_button(self) -> None:
        """
        Click on the Facebook login / register button, that appears in the modal.
        :return: None
        """
        # Wait until the facebook button is ready to be clickable (e.g. Wait for the loading to be done).
        WebDriverWait(self.browser, 15).until(
            EC.element_to_be_clickable((By.XPATH, "//button[div[text()='Kirim kode via Whatsapp']]"))
        )
        facebook_login_button: WebElement = WebDriverWait(self.browser, 10).until(
            EC.element_to_be_clickable((By.XPATH, "//span[text()='Facebook']"))
        )
        facebook_login_button.click()

    def switch_window(self, window: int) -> None:
        """
        Switch to either Facebook or Lazada window.
        :return: None
        """
        self.browser.switch_to.window(self.browser.window_handles[window])

    def type_email(self, email: str) -> None:
        """
        Type the email into the email field.
        :param email: The user's email
        :return: None
        """
        email_field: WebElement = WebDriverWait(self.browser, 10).until(
            EC.presence_of_element_located((By.ID, "email"))
        )
        email_field.send_keys(email)

    def type_password(self, password: str) -> None:
        """
        Type the password into the password field.
        :param password: The user's password
        :return: None
        """
        password_field: WebElement = WebDriverWait(self.browser, 10).until(
            EC.presence_of_element_located((By.ID, "pass"))
        )
        password_field.send_keys(password)

    def click_login_button(self) -> None:
        """
        Click on the login button.
        :return: None
        """
        login_button: WebElement = WebDriverWait(self.browser, 10).until(
            EC.presence_of_element_located((By.NAME, "login"))
        )
        login_button.click()

    def click_continue_as_button(self) -> None:
        """
        Click on the continue as button, this should only happen after login.
        :return: None
        """
        lanjutkan_button: WebElement = WebDriverWait(self.browser, 10).until(
            EC.presence_of_element_located((By.XPATH, "//span[contains(normalize-space(.), 'Lanjutkan sebagai')]"))
        )
        lanjutkan_button.click()
