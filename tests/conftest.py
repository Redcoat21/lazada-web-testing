# This file is used to set up the browser and the driver for the tests.
import pytest
from selenium import webdriver
from webdriver_manager.firefox import GeckoDriverManager
from selenium.webdriver.firefox.service import Service
from selenium.webdriver.firefox.webdriver import WebDriver
from selenium.webdriver.firefox.options import Options
from typing import Generator
from dotenv import load_dotenv
import os

# Load environment variables from .env file
load_dotenv()


@pytest.fixture(scope="function", autouse=True)
def browser() -> Generator[WebDriver, None, None]:
    options = Options()
    options.headless = True
    extension_path = os.getenv("CAPTCHA_SOLVER_EXTENSION_PATH")
    service = Service(GeckoDriverManager().install())
    driver = webdriver.Firefox(service=service, options=options)
    # driver.install_addon(extension_path, temporary=True)

    base_url = "https://lazada.co.id"

    driver.get(base_url)
    yield driver
    driver.quit()
