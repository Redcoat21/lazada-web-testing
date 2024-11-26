# This file is used to setup the browser and the driver for the tests.
import pytest
from selenium import webdriver
from webdriver_manager.firefox import GeckoDriverManager
from selenium.webdriver.firefox.service import Service
from selenium.webdriver.firefox.webdriver import WebDriver
from typing import Generator

@pytest.fixture(scope="module")
def browser() -> Generator[WebDriver, None, None]:
    service = Service(GeckoDriverManager().install())
    driver = webdriver.Firefox(service=service)
    
    base_url = "https://lazada.co.id"

    driver.get(base_url)
    yield driver
    driver.quit()