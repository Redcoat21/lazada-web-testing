from time import sleep

from selenium.webdriver.common.by import By
from selenium.webdriver.ie.webdriver import WebDriver
from selenium.webdriver.remote.webelement import WebElement
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


class FacebookTestHelper:
    def __init__(self, browser: WebDriver):
        self.browser = browser

    def click_on_facebook_button(self):
        """
        Click on the Facebook login button.
        :return: None
        """
        facebook_login_button: WebElement = WebDriverWait(self.browser, 10).until(
            EC.element_to_be_clickable((By.XPATH, "//span[text()='Facebook']"))
        )
        # Sleep for 1 second to allow the facebook button to be able to be clicked properly
        sleep(5)
        facebook_login_button.click()

    def switch_to_facebook_window(self):
        """
        Switch to the Facebook login window.
        :return: None
        """
        self.browser.switch_to.window(self.browser.window_handles[1])

    def switch_to_lazada_window(self):
        """
        Switch to the Lazada window.
        :return: None
        """
        self.browser.switch_to.window(self.browser.window_handles[0])

    def type_facebook_email(self, email: str):
        """
        Type the email into the email field.
        :param email: the facebook user's email
        :return: None
        """
        email_field: WebElement = WebDriverWait(self.browser, 10).until(
            EC.presence_of_element_located((By.ID, "email"))
        )
        email_field.send_keys(email)

    def type_facebook_password(self, password: str):
        """
        Type the password into the password field.
        :param password: the facebook user's password
        :return: None
        """
        password_field: WebElement = WebDriverWait(self.browser, 10).until(
            EC.presence_of_element_located((By.ID, "pass"))
        )
        password_field.send_keys(password)

    def click_on_login_button(self):
        """
        Click on the login button IN THE FACEBOOK MODAL.
        :return: None
        """
        login_button: WebElement = WebDriverWait(self.browser, 10).until(
            EC.presence_of_element_located((By.NAME, "login"))
        )
        login_button.click()
