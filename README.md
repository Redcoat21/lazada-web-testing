# Lazada Web Testing

This is a project to test Lazada website, Lazada is an online shopping platform originated from Indonesia. In this project, we will use Selenium and Python.

## Setup

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-repo/lazada-web-testing.git
   cd lazada-web-testing
   ```

2. **Install Dependencies:**
   ```bash
   pip install -r requirements.txt
   ```

3. **Set Up Environment Variables:**
   - Copy the `.env.example` file to `.env`:
     ```bash
     cp .env.example .env
     ```
   - Open the `.env` file and update the following variables:
     ```plaintext
     FACEBOOK_EMAIL=your_facebook_email@example.com
     FACEBOOK_PASSWORD=your_facebook_password
     CAPTCHA_SOLVER_EXTENSION_PATH='extensions/{2f67aecb-5dac-4f76-9378-0ac4f2bedc9c}.xpi'
     ```

4. **Run the Tests:**
   ```bash
   py -3.12 -m pytest tests/test_login.py
   ```

## Project Structure

- `tests/`: Contains all the test scripts.
  - `test_login.py`: Tests the login functionality using Facebook.
  - `test_register.py`: Tests the registration functionality.
- `extensions/`: Contains the captcha solver extension.
  - `{2f67aecb-5dac-4f76-9378-0ac4f2bedc9c}.xpi`: Captcha solver extension for Selenium.
- `README.md`: This file.
- `requirements.txt`: Lists all the Python dependencies.
- `.env.example`: Example environment variables file.
- `.env`: Environment variables file (should be created from `.env.example`).

## Notes

- Ensure that the captcha solver extension is correctly placed in the `extensions` directory.
- The Facebook email and password should be kept secure and not shared publicly.
