# Selenium POM Framework - Quick Reference Guide

## Project Structure at a Glance

```
src/test/java/com/MCP_Automation/
├── base/
│   └── BasePage.java                  # ← Base class for all page objects
├── pages/
│   └── LoginPage.java                 # ← Page object (locators + methods)
├── config/
│   └── TestConfig.java                # ← Centralized configuration
├── factory/
│   └── DriverFactory.java             # ← Creates WebDriver instances
├── utils/
│   └── SeleniumUtil.java              # ← Helper utilities
├── reporting/
│   └── TestReport.java                # ← Custom HTML/Text reporting
├── listeners/
│   └── TestListener.java              # ← JUnit 5 event listeners
└── Login.java                         # ← Sample test using POM

test-reports/                          # ← Generated HTML/Text reports
screenshots/                           # ← Generated screenshots on failure
```

## Core Concepts

### 1. Page Object Model (POM)
- **What**: Each page is represented by a Java class
- **Why**: Separates test logic from element locators
- **How**: Page classes extend `BasePage` and use `@FindBy` annotations

### 2. Base Page Pattern
- **What**: Common Selenium operations in one place
- **Why**: Reduces code duplication
- **Methods**: `waitForElementToBeVisible()`, `clickElement()`, `clearAndEnterText()`, etc.

### 3. Factory Pattern
- **What**: Creates WebDriver instances
- **Why**: Supports multiple browsers (Chrome, Firefox, Edge)
- **Usage**: `driver = DriverFactory.createDriver("chrome")`

### 4. Configuration Management
- **What**: Centralized test data and settings
- **Why**: Easy to update without modifying test code
- **Usage**: `TestConfig.VALID_USERNAME`, `TestConfig.BASE_URL`

## Essential Methods Reference

### BasePage Methods
```java
// Wait methods
waitForElementToBeVisible(WebElement element)
waitForElementToBeClickable(WebElement element)

// Action methods
clearAndEnterText(WebElement element, String text)
clickElement(WebElement element)

// Get methods
getPageTitle()
getCurrentUrl()
getElementText(WebElement element)

// Navigation
navigateTo(String url)
maximizeWindow()
```

### SeleniumUtil Methods
```java
// Screenshot and timestamps
takeScreenshot(WebDriver driver, String screenshotName)
getTimestamp()
getFormattedTimestamp()

// Wait and scroll
waitForSeconds(int seconds)
scrollPage(WebDriver driver, int yOffset)

// JavaScript
executeJavaScript(WebDriver driver, String script)

// Logging
log(String message)
errorLog(String message)
```

### TestReport Methods
```java
logPass(String stepName, String description)
logFail(String stepName, String description)
logInfo(String stepName, String description)
generateHTMLReport()
generateTextReport()
getPassCount()
getFailCount()
```

## Common Tasks

### Create New Page Object
```java
public class DashboardPage extends BasePage {
    @FindBy(xpath = "//h6[contains(text(), 'Dashboard')]")
    private WebElement title;
    
    public DashboardPage(WebDriver driver) {
        super(driver);
    }
    
    public String getTitle() {
        return getElementText(title);
    }
}
```

### Create New Test
```java
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
            report.logInfo("Step 1", "Verify title");
            String title = dashboardPage.getTitle();
            assertEquals("Dashboard", title);
            report.logPass("Title Verification", "Title is correct");
        } catch (Exception e) {
            report.logFail("Test", e.getMessage());
            SeleniumUtil.takeScreenshot(driver, "test_failed");
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

### Update Configuration
Edit `TestConfig.java`:
```java
// Change test data
public static final String VALID_USERNAME = "NewUsername";

// Change timeout
public static final int EXPLICIT_WAIT_TIMEOUT = 15;

// Change URL
public static final String BASE_URL = "https://new-url.com";
```

## Running Tests

```bash
# Compile project
mvn clean compile -DskipTests

# Compile with tests
mvn clean test-compile

# Run all tests
mvn clean test

# Run specific test
mvn clean test -Dtest=Login

# Run with specific browser
mvn test -Dbrowser=firefox

# Run with headless mode
mvn test -Dheadless=true
```

## File Locations

- **Test Reports**: `test-reports/*.html` and `test-reports/*.txt`
- **Screenshots**: `screenshots/*.png`
- **Configuration**: `src/test/java/com/MCP_Automation/config/TestConfig.java`
- **Test Data**: `TestConfig.java` (VALID_USERNAME, VALID_PASSWORD, etc.)

## Debugging Tips

1. **Element Not Found**
   - Verify locator in Page Object
   - Use `SeleniumUtil.takeScreenshot()` to see page state
   - Check if element is visible (use `waitForElementToBeVisible()`)

2. **Test Timeout**
   - Increase `EXPLICIT_WAIT_TIMEOUT` in TestConfig
   - Check application responsiveness
   - Verify network connectivity

3. **Report Not Generated**
   - Check `test-reports/` directory exists
   - Verify write permissions
   - Check disk space

## Best Practices Checklist

- [ ] One page object per page
- [ ] Use descriptive method names
- [ ] Use explicit waits (BasePage methods)
- [ ] Store locators in page objects only
- [ ] Use TestConfig for test data
- [ ] Always generate reports
- [ ] Take screenshots on failures
- [ ] Use SeleniumUtil.log() for tracking
- [ ] Handle exceptions properly
- [ ] Close WebDriver in tearDown()

## Useful Links

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [JUnit 5 Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Page Object Model Pattern](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/)

---

**Version**: 1.0  
**Last Updated**: 2024
