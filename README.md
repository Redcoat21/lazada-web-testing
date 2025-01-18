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
   ./gradlew build
   ```

3. **Set Up Environment Variables:**
   - Copy the `.env.example` file to `.env`:
     ```bash
     cp .env.example .env
     ```
   - Notes for the environment variables:
     - `USE_LOCAL_BROWSER` should be set into either true or false. This variable is used to determine whether to use local browser or remote browser.
     - Note that when `USE_LOCAL_BROWSER` is set to false, it will use selenoid or an existing web driver.
     - `BROWSER_TYPE` should be either `chrome` or `firefox` or `edge`. This variable is used to determine the browser type.
     - When in doubt, just set `BROWSER_TYPE` to `firefox` and `USE_LOCAL_BROWSER` to `true`.

4. **Build the project**
    ```bash
    ./gradlew build
    ```
   
4. **Run the Tests:**
   ```bash
   ./gradlew test
   ```

## Project Structure

- `src/test/kotlin`: Contains all the tests.
  - `src/test/com/softwaretesting/helper`: Contain helper class for the test.
  - `src/test/com/softwaretesting/lazadawebtesting`: Contain the test class.
- `README.md`: This file.
- `.env.example`: Example environment variables file.
- `.env`: Environment variables file (should be created from `.env.example`).

