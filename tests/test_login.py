import pytest
from selenium.webdriver.chrome.webdriver import WebDriver
from selenium.webdriver.common.by import By
from selenium.webdriver.remote.webelement import WebElement
from time import sleep
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import os


class BaseTestLogin:
    browser: WebDriver

    @pytest.fixture(scope="function", autouse=True)
    def login(self, browser: WebDriver):
        """
        Fixture to ensure before every test, the login modal is opened.
        :param browser: browser should automatically injected from conftest.py
        :return: WebDriver - The browser instance.
        """
        self.browser = browser
        self._click_on_login_link()
        yield browser

    def _click_on_login_link(self):
        """
        Click on the login link to pop up the login modal.
        :return: None
        """
        login_link: WebElement = self.browser.find_element(by=By.ID, value="anonLogin")
        login_link.click()


class TestLocalLogin(BaseTestLogin):
    pass


class TestGoogleLogin(BaseTestLogin):
    pass


class TestFacebookLogin(BaseTestLogin):
    @pytest.fixture(scope="function", autouse=True)
    def _facebook_login(self, login: WebDriver):
        self._click_on_facebook_login_button()
        self._switch_to_facebook_window()

    def _click_on_facebook_login_button(self):
        """
        Click on the Facebook login button.
        :return: None
        """
        facebook_login_button: WebElement = WebDriverWait(self.browser, 10).until(
            EC.presence_of_element_located((By.XPATH, "//span[text()='Facebook']"))
        )
        facebook_login_button.click()

    def _switch_window(self, window_index: int):
        """
        Switch to the window with the specified index.
        :param window_index: the index of the window.
        :return: None
        """
        self.browser.switch_to.window(self.browser.window_handles[window_index])

    def _switch_to_facebook_window(self):
        """
        Switch to the Facebook login window.
        :return: None
        """
        # Wait until the facebook login window pop up.
        WebDriverWait(self.browser, 10).until(lambda d: len(d.window_handles) > 1)
        self._switch_window(1)

    def _type_facebook_email(self, email: str):
        """
        Type the email into the email field.
        :param email: the facebook user's email
        :return: None
        """
        email_field: WebElement = WebDriverWait(self.browser, 10).until(
            EC.presence_of_element_located((By.ID, "email"))
        )
        email_field.send_keys(os.getenv(email))

    def _type_facebook_password(self, password: str):
        """
        Type the password into the password field.
        :param password: the facebook user's password
        :return: None
        """
        password_field: WebElement = WebDriverWait(self.browser, 10).until(
            EC.presence_of_element_located((By.ID, "pass"))
        )
        password_field.send_keys(os.getenv(password))

    def _click_on_login_button(self):
        """
        Click on the login button IN THE FACEBOOK MODAL.
        :return: None
        """
        login_button: WebElement = WebDriverWait(self.browser, 10).until(
            EC.presence_of_element_located((By.NAME, "login"))
        )
        login_button.click()

    def test_login_with_facebook_valid(self):
        """
        Test the login with a valid Facebook account.
        :return: None
        """
        self._type_facebook_email(os.getenv("FACEBOOK_EMAIL"))
        self._type_facebook_password(os.getenv("FACEBOOK_PASSWORD"))
        self._click_on_login_button()

        # Click on the continue as button.
        continue_as_button: WebElement = WebDriverWait(self.browser, 10).until(
            EC.presence_of_element_located(
                (By.XPATH, "//span[contains(normalize-space(.), 'Lanjutkan sebagai')]")
            )
        )
        continue_as_button.click()

        # Switch back to the Lazada window
        self._switch_window(0)

        # BUG: Kayaknya yang ini problematik, soalnya text Welcome back ga bakal muncul.
        # Wait for the welcome back text to appear.
        WebDriverWait(self.browser, 10).until(
            EC.presence_of_element_located(
                (By.XPATH, "//div[contains(text(), 'Welcome back')]")
            )
        )

        assert (
            "https://www.lazada.co.id/" in self.browser.current_url
        ), "User should be redirected to the homepage"
