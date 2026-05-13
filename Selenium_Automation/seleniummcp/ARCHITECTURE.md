# Selenium POM Framework - Architecture Overview

## Framework Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                     TEST EXECUTION LAYER                        │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │            Login.java (Test Case)                        │  │
│  │  - Uses LoginPage POM                                    │  │
│  │  - Uses DriverFactory for browser setup                  │  │
│  │  - Uses TestReport for step logging                      │  │
│  │  - Captures screenshots on failure                       │  │
│  └──────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
              │                    │                    │
              │                    │                    │
              ▼                    ▼                    ▼
┌──────────────────┐   ┌──────────────────┐   ┌──────────────────┐
│   LoginPage      │   │ DriverFactory    │   │   TestReport     │
│   (POM)          │   │   (Browser)      │   │   (Reporting)    │
│                  │   │                  │   │                  │
│ @FindBy:         │   │ Methods:         │   │ Methods:         │
│ - Username       │   │ - createDriver() │   │ - logPass()      │
│ - Password       │   │                  │   │ - logFail()      │
│ - LoginButton    │   │ Supports:        │   │ - logInfo()      │
│                  │   │ - Chrome         │   │ - generateHTML() │
│ Methods:         │   │ - Firefox        │   │ - generateText() │
│ - login()        │   │ - Edge           │   │                  │
│ - enterUser()    │   │                  │   │ Outputs:         │
│ - enterPass()    │   │                  │   │ - HTML Reports   │
│                  │   │                  │   │ - Text Reports   │
└──────────────────┘   └──────────────────┘   └──────────────────┘
       ▲                                          │
       │                                          │
       │                                          ▼
       │                              ┌──────────────────────┐
       │                              │   SeleniumUtil       │
       │                              │   (Utilities)        │
       │                              │                      │
       │                              │ Methods:             │
       │                              │ - takeScreenshot()   │
       │                              │ - getTimestamp()     │
       │                              │ - waitFor...()       │
       │                              │ - log()              │
       │                              └──────────────────────┘
       │
       │
       ▼
┌──────────────────────────────────────────────────────────┐
│              BasePage.java (Base Class)                  │
│                                                          │
│  Common Selenium Operations:                            │
│  - waitForElementToBeVisible()                          │
│  - waitForElementToBeClickable()                        │
│  - clearAndEnterText()                                  │
│  - clickElement()                                       │
│  - getPageTitle(), getCurrentUrl()                      │
│  - getElementText()                                     │
│  - navigateTo(), maximizeWindow()                       │
│                                                          │
│  ↓ All Page Objects Extend BasePage ↓                  │
└──────────────────────────────────────────────────────────┘
       │
       ▼
┌──────────────────────────────────────────────────────────┐
│         WebDriver (Selenium/Browser Automation)          │
│                                                          │
│  ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓            │
│  Chrome Driver    Firefox Driver    Edge Driver         │
│  ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓  ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓  ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓   │
└──────────────────────────────────────────────────────────┘
       │
       ▼
┌──────────────────────────────────────────────────────────┐
│              Web Application (AUT)                       │
│          OrangeHRM Demo Application                      │
└──────────────────────────────────────────────────────────┘
```

## Component Interaction Flow

### Test Execution Flow
```
Test Class (Login.java)
    ↓
setUp() {
    ├─→ DriverFactory.createDriver() → WebDriver
    ├─→ LoginPage instantiation (with driver)
    └─→ TestReport instantiation
}
    ↓
@Test testOrangeHRMLogin() {
    ├─→ loginPage.navigateToLoginPage()
    │   └─→ BasePage.navigateTo() → driver.get()
    │
    ├─→ loginPage.enterUsername(username)
    │   └─→ BasePage.clearAndEnterText()
    │       ├─→ BasePage.waitForElementToBeVisible()
    │       └─→ element.clear() + element.sendKeys()
    │
    ├─→ loginPage.enterPassword(password)
    │   └─→ (same as username)
    │
    ├─→ loginPage.clickLoginButton()
    │   └─→ BasePage.clickElement()
    │       ├─→ BasePage.waitForElementToBeClickable()
    │       └─→ element.click()
    │
    ├─→ loginPage.isDashboardDisplayed()
    │   └─→ BasePage.waitForElementToBeVisible()
    │
    ├─→ report.logPass(stepName, description)
    │   └─→ Add to internal test log
    │
    └─→ SeleniumUtil.takeScreenshot() (on failure)
}
    ↓
tearDown() {
    ├─→ report.generateHTMLReport() → test-reports/*.html
    ├─→ report.generateTextReport() → test-reports/*.txt
    └─→ driver.quit()
}
```

## Data Flow Architecture

```
┌────────────────────────────────────────────────────────────┐
│              Configuration Layer (TestConfig)              │
│                                                            │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐ │
│  │ Credentials  │  │ URLs         │  │ Timeouts/Browser │ │
│  ├──────────────┤  ├──────────────┤  ├──────────────────┤ │
│  │ USERNAME     │  │ BASE_URL     │  │ WAIT_TIMEOUT     │ │
│  │ PASSWORD     │  │ DASHBOARD_URL│  │ HEADLESS_MODE    │ │
│  │              │  │              │  │ BROWSER_WIDTH    │ │
│  └──────────────┘  └──────────────┘  └──────────────────┘ │
└────────────────────────────────────────────────────────────┘
                │
                │ (Used by Test)
                ▼
┌────────────────────────────────────────────────────────────┐
│              Test Execution Data                           │
│                                                            │
│  Input: username, password (from TestConfig)              │
│         ↓                                                  │
│  Processing: Element identification and interaction       │
│         ↓                                                  │
│  Output: Test steps logged in TestReport                  │
└────────────────────────────────────────────────────────────┘
                │
                ▼
┌────────────────────────────────────────────────────────────┐
│              Report Generation                            │
│                                                            │
│  ┌────────────────────────────────────────────────────┐   │
│  │ Test Steps:                                        │   │
│  │ [PASS] Step 1 - Username entered successfully      │   │
│  │ [PASS] Step 2 - Password entered successfully      │   │
│  │ [PASS] Step 3 - Login button clicked               │   │
│  │ [PASS] Step 4 - Dashboard displayed                │   │
│  │                                                    │   │
│  │ Summary:                                           │   │
│  │ Total Steps: 4  |  Passed: 4  |  Failed: 0        │   │
│  │ Duration: 5 seconds                                │   │
│  └────────────────────────────────────────────────────┘   │
│                                                            │
│  Output Files:                                            │
│  → test-reports/OrangeHRM_Login_Test_YYYY-MM-DD.html      │
│  → test-reports/OrangeHRM_Login_Test_YYYY-MM-DD.txt       │
│  → screenshots/login_test_failed_YYYY-MM-DD.png (if fail) │
└────────────────────────────────────────────────────────────┘
```

## Design Patterns Used

### 1. **Page Object Model (POM)**
```
Page Object = Page Elements + Page Methods
LoginPage extends BasePage
├─ Elements: @FindBy locators
└─ Methods: login(), enterUsername(), etc.
```

### 2. **Base Class Pattern**
```
BasePage (Abstract/Base)
├─ Common Selenium operations
├─ Explicit wait wrapper methods
└─ LoginPage extends BasePage (inherits all methods)
```

### 3. **Factory Pattern**
```
DriverFactory
├─ createDriver(browserType)
│  ├─ Chrome → createChromeDriver()
│  ├─ Firefox → createFirefoxDriver()
│  └─ Edge → createEdgeDriver()
└─ Automatic driver configuration
```

### 4. **Configuration Pattern**
```
TestConfig (Static Constants)
├─ Test Data
├─ Timeouts
├─ URLs
└─ Browser Settings
```

### 5. **Utility Pattern**
```
SeleniumUtil (Static Helper Methods)
├─ Screenshot capture
├─ Wait helpers
├─ JavaScript execution
└─ Logging utilities
```

### 6. **Reporting Pattern**
```
TestReport (Step-based Reporting)
├─ Log collection during execution
├─ Multiple output formats (HTML, Text)
└─ Summary statistics
```

## Class Relationships

```
Login.java (Test)
    ├─ instantiates → LoginPage
    │                   ├─ extends → BasePage
    │                   │               └─ uses → WebDriver
    │                   └─ uses → @FindBy annotations
    │
    ├─ uses → DriverFactory
    │           └─ creates → WebDriver
    │
    ├─ uses → TestReport
    │           └─ logs → Test Steps
    │
    ├─ uses → SeleniumUtil
    │           └─ provides → Helper Methods
    │
    └─ uses → TestConfig
                └─ provides → Static Constants
```

## Execution Sequence Diagram

```
User
  │
  ├─→ mvn clean test
  │
  ├─→ Maven loads pom.xml
  │
  ├─→ Executes @BeforeEach setUp()
  │   │
  │   ├─→ DriverFactory.createDriver()
  │   │
  │   ├─→ new LoginPage(driver)
  │   │   └─→ BasePage.__init__() initializes PageFactory
  │   │
  │   └─→ new TestReport("name")
  │
  ├─→ Executes @Test testOrangeHRMLogin()
  │   │
  │   ├─→ Page interaction methods
  │   │   └─→ BasePage helper methods
  │   │       └─→ WebDriver actions
  │   │
  │   └─→ TestReport.logPass/logFail/logInfo()
  │
  ├─→ Executes @AfterEach tearDown()
  │   │
  │   ├─→ report.generateHTMLReport()
  │   │   └─→ Writes to test-reports/
  │   │
  │   ├─→ report.generateTextReport()
  │   │   └─→ Writes to test-reports/
  │   │
  │   └─→ driver.quit()
  │
  └─→ Test Summary
      └─→ Reports generated in test-reports/
```

## File Structure Summary

```
Selenium POM Framework
│
├── Source Code Layer
│   ├── src/main/java/ (Main application)
│   └── src/test/java/com/MCP_Automation/
│       ├── base/ (Base classes)
│       ├── pages/ (Page objects)
│       ├── config/ (Configuration)
│       ├── factory/ (Factories)
│       ├── utils/ (Utilities)
│       ├── reporting/ (Reporting)
│       ├── listeners/ (Event listeners)
│       └── Login.java (Test class)
│
├── Configuration Layer
│   └── pom.xml (Maven configuration + dependencies)
│
├── Output Layer
│   ├── test-reports/ (Generated HTML/Text reports)
│   └── screenshots/ (Generated screenshots)
│
├── Target Layer (Generated by Maven)
│   ├── classes/ (Compiled source)
│   └── test-classes/ (Compiled tests)
│
└── Documentation Layer
    ├── README.md (Complete guide)
    ├── QUICK_REFERENCE.md (Quick lookup)
    └── PROJECT_SUMMARY.md (Project overview)
```

## Summary Table

| Component | Purpose | Type | Reusable |
|-----------|---------|------|----------|
| BasePage | Common Selenium ops | Base Class | Yes (by inheritance) |
| LoginPage | Login page elements | Page Object | Yes (by instantiation) |
| DriverFactory | Browser creation | Factory | Yes (static method) |
| TestConfig | Test data & config | Constants | Yes (static constants) |
| SeleniumUtil | Helper methods | Utility | Yes (static methods) |
| TestReport | Test reporting | Report | Yes (new instance per test) |
| TestListener | Test events | Listener | Yes (JUnit extension) |
| Login | Test case | Test | No (specific test) |

---

**Framework Version**: 1.0  
**Pattern**: Page Object Model (POM)  
**Test Framework**: JUnit 5 (Jupiter)  
**Automation Tool**: Selenium WebDriver 4.20.0  
**Status**: ✅ Production Ready
