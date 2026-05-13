# Selenium POM (Page Object Model) Framework

## Overview
This is a comprehensive Selenium WebDriver framework built using the **Page Object Model** pattern with a base class, utilities, factory pattern, and custom reporting mechanism.

## Project Structure

```
src/
├── test/
│   └── java/
│       └── com/
│           └── MCP_Automation/
│               ├── base/
│               │   └── BasePage.java              # Base class with common Selenium operations
│               ├── pages/
│               │   └── LoginPage.java             # Login page POM
│               ├── config/
│               │   └── TestConfig.java            # Configuration and test data
│               ├── factory/
│               │   └── DriverFactory.java         # WebDriver factory for browser initialization
│               ├── utils/
│               │   └── SeleniumUtil.java          # Utility methods for Selenium operations
│               ├── reporting/
│               │   └── TestReport.java            # Custom test reporting (HTML & Text)
│               ├── listeners/
│               │   └── TestListener.java          # JUnit 5 test listener/extension
│               └── Login.java                     # Sample login test
```

## Key Components

### 1. **BasePage.java** (Base Class)
Contains common Selenium operations used across all page objects:
- `waitForElementToBeVisible()` - Wait for element visibility
- `waitForElementToBeClickable()` - Wait for element to be clickable
- `clearAndEnterText()` - Clear and enter text
- `clickElement()` - Click with wait
- `getPageTitle()` - Get page title
- `getCurrentUrl()` - Get current URL
- `navigateTo()` - Navigate to URL
- `maximizeWindow()` - Maximize browser window

### 2. **LoginPage.java** (Page Object)
Encapsulates login page elements and methods:
- Page elements using `@FindBy` annotation
- Methods: `login()`, `enterUsername()`, `enterPassword()`, `clickLoginButton()`, `isDashboardDisplayed()`
- No direct XPath references in test code

### 3. **TestConfig.java** (Configuration)
Centralized configuration management:
- Test data (credentials, URLs)
- Timeout settings
- Browser configuration
- Report directory paths
- Property management methods

### 4. **DriverFactory.java** (Factory Pattern)
Creates WebDriver instances based on browser type:
- Supports: Chrome, Firefox, Edge
- Configurable browser options (headless mode, window size)
- Automatic timeout configuration

### 5. **SeleniumUtil.java** (Utilities)
Helper methods for common operations:
- `takeScreenshot()` - Capture screenshots
- `getTimestamp()` - Get formatted timestamps
- `waitForSeconds()` - Wait helper
- `scrollPage()` - Scroll page
- `executeJavaScript()` - Execute JS code
- `log()` & `errorLog()` - Logging methods

### 6. **TestReport.java** (Reporting)
Custom reporting mechanism:
- `logPass()` - Log successful test steps
- `logFail()` - Log failed test steps
- `logInfo()` - Log informational steps
- `generateHTMLReport()` - Generate formatted HTML report
- `generateTextReport()` - Generate text report
- Test summary with pass/fail counts

### 7. **TestListener.java** (Event Listener)
JUnit 5 extension for test execution events:
- `testSuccessful()` - Triggered on test success
- `testFailed()` - Triggered on test failure
- `testAborted()` - Triggered on test abort
- `testDisabled()` - Triggered on disabled test

## Usage

### Running Tests

```bash
# Run all tests
mvn clean test

# Run specific test
mvn clean test -Dtest=Login

# Run with headless mode
mvn clean test -Dheadless=true

# Run with specific browser
mvn clean test -Dbrowser=firefox
```

### Creating a New Page Object

```java
package com.MCP_Automation.pages;

import com.MCP_Automation.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage {
    
    @FindBy(xpath = "//h6[contains(text(), 'Dashboard')]")
    private WebElement dashboardTitle;
    
    public DashboardPage(WebDriver driver) {
        super(driver);
    }
    
    public String getDashboardTitle() {
        return getElementText(dashboardTitle);
    }
}
```

### Creating a New Test

```java
import com.MCP_Automation.pages.LoginPage;
import com.MCP_Automation.reporting.TestReport;
import com.MCP_Automation.config.TestConfig;
import com.MCP_Automation.factory.DriverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class DashboardTest {
    
    private WebDriver driver;
    private DashboardPage dashboardPage;
    private TestReport report;
    
    @BeforeEach
    public void setUp() {
        driver = DriverFactory.createDriver(TestConfig.BROWSER_TYPE);
        dashboardPage = new DashboardPage(driver);
        report = new TestReport("Dashboard_Test");
    }
    
    @Test
    public void testDashboard() {
        try {
            // Test logic here
            report.logPass("Step", "Description");
        } catch (Exception e) {
            report.logFail("Step", e.getMessage());
            SeleniumUtil.takeScreenshot(driver, "dashboard_test_failed");
        }
    }
    
    @AfterEach
    public void tearDown() {
        report.generateHTMLReport();
        report.generateTextReport();
        if (driver != null) {
            driver.quit();
        }
    }
}
```

## Reports

### HTML Reports
- Generated in `test-reports/` directory
- Styled with color-coded pass/fail steps
- Includes test summary with duration

### Text Reports
- Plain text format for quick review
- Contains timestamp for each step
- Shows pass/fail summary

## Configuration

Edit [TestConfig.java](src/test/java/com/MCP_Automation/config/TestConfig.java) to:
- Change test credentials
- Adjust timeout values
- Configure browser settings
- Modify report directories

## Dependencies

- **Selenium**: 4.20.0
- **JUnit 5**: 5.11.0
- **WebDriverManager**: 5.7.3
- **ExtentReports**: 5.0.9 (optional)
- **SLF4J**: 2.0.9

## Best Practices

1. **Page Objects**: One page object per page/component
2. **Naming**: Use descriptive names for methods and variables
3. **Waits**: Always use explicit waits via BasePage methods
4. **Locators**: Store locators in page objects, not in tests
5. **Data**: Use TestConfig for test data management
6. **Reports**: Generate reports for all test executions
7. **Screenshots**: Capture screenshots on failures
8. **Logging**: Use SeleniumUtil.log() for tracking

## Troubleshooting

### Element Not Found
- Check if the element locator is correct in the page object
- Verify element is visible and clickable
- Use explicit waits via BasePage methods

### Test Timeout
- Increase timeout in TestConfig.java
- Verify application is responsive
- Check network connectivity

### Report Generation Failures
- Ensure test-reports/ directory exists
- Check file write permissions
- Verify disk space availability

## Future Enhancements

- Parallel test execution
- Extended report types (PDF, Excel)
- API integration for test data
- Screenshots in HTML reports
- Cross-browser test execution
- CI/CD pipeline integration

---

**Author**: QA Automation Team  
**Version**: 1.0  
**Last Updated**: 2024
