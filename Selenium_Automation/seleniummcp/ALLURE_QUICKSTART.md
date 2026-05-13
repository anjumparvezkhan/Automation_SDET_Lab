# Allure Report - Quick Start Guide

## ✅ What's Been Configured

Your Selenium project now includes:

1. **Allure Report Dependencies** - Added to pom.xml
2. **Allure JUnit5 Integration** - Automatic test listener
3. **AllureReporting Utility** - Helper methods for reports
4. **Test Annotations** - Epic, Feature, Story, Severity, Owner
5. **Allure Configuration** - allure.properties file

## 📦 Installation

### Step 1: Install Allure Command Line
Download and install from: https://github.com/allure-framework/allure2/releases

**Windows via Chocolatey:**
```bash
choco install allure
```

**Verify Installation:**
```bash
allure --version
```

## 🚀 Running Tests with Allure Reports

### Option 1: Run Tests and View Report Immediately (Recommended)
```bash
cd c:\MyWorkspace\Selenium\seleniummcp
mvn clean test && allure serve allure-results/
```

This will:
- Run all tests
- Generate Allure results in `allure-results/`
- Automatically open the beautiful HTML report in your browser

### Option 2: Run Tests Only
```bash
mvn clean test
```

Then generate and view the report:
```bash
allure serve allure-results/
```

### Option 3: Generate Report Without Serving
```bash
# Generate report in allure-report/ directory
allure generate allure-results/ --clean

# Open manually
allure open allure-report/
```

## 📊 What You'll See in the Report

- **Overview Dashboard** - Pass/Fail statistics, execution timeline
- **Test Results** - Detailed view of each test with steps
- **Categories** - Tests organized by Epic, Feature, Story
- **Timeline** - Visual execution timeline
- **Behaviors** - Hierarchical test organization
- **Screenshots & Attachments** - Visual proof of test execution
- **History** - Track test results over multiple runs

## 🔍 Key Features in Your Tests

### Test Metadata
```java
@Epic("Authentication")        // High-level feature group
@Feature("User Login")          // Specific feature
@Story("Valid user login")      // Test scenario
@DisplayName("Login Test")      // Human-readable name
@Severity(SeverityLevel.CRITICAL) // Test importance
@Owner("QA Team")               // Test owner
```

### Allure Annotations in Code
```java
// Automatic step recording
Allure.step("Step Description", () -> {
    // Step implementation
});

// Attach screenshots
AllureReporting.attachScreenshot(driver, "Screenshot Name");

// Attach text
AllureReporting.attachText("Details", "Content here");

// Attach page source
AllureReporting.attachPageSource(driver);

// Add links
AllureReporting.addLink("Documentation", "https://example.com");
AllureReporting.addIssueLink("BUG-123");
AllureReporting.addTestCaseLink("TC-456");
```

## 📁 Output Locations

| Path | Contents | Purpose |
|------|----------|---------|
| `allure-results/` | JSON test data | Raw test results (used by Allure) |
| `allure-report/` | HTML report | Generated HTML report |
| `test-reports/` | Custom reports | Backward compatibility |
| `screenshots/` | PNG files | Failure screenshots |

## 🛠️ Useful Commands

```bash
# Run tests and serve report (all-in-one)
mvn clean test && allure serve allure-results/

# Generate report without serving
allure generate allure-results/ --clean

# Open previously generated report
allure open allure-report/

# Clear and regenerate
rm -rf allure-results/ allure-report/
mvn clean test
allure serve allure-results/

# Check version
allure --version
```

## 📝 Example: Creating a New Test with Allure

```java
@Epic("Authentication")
@Feature("User Management")
@Story("Admin can login")
@DisplayName("Verify admin login with valid credentials")
@Severity(SeverityLevel.CRITICAL)
@Owner("QA Team")
public void testAdminLogin() {
    try {
        // Step 1
        Allure.step("Navigate to login page", () -> {
            loginPage.navigateToLoginPage();
            AllureReporting.attachScreenshot(driver, "LoginPage_Loaded");
        });
        
        // Step 2
        Allure.step("Enter credentials", () -> {
            loginPage.enterUsername("admin");
            loginPage.enterPassword("admin123");
        });
        
        // Step 3
        Allure.step("Submit login form", () -> {
            loginPage.clickLoginButton();
        });
        
        // Step 4 - Verification
        Allure.step("Verify successful login", () -> {
            assertTrue(loginPage.isDashboardDisplayed());
            AllureReporting.attachScreenshot(driver, "Dashboard");
        });
        
    } catch (Exception e) {
        AllureReporting.attachScreenshot(driver, "Failure");
        AllureReporting.attachPageSource(driver);
        throw e;
    }
}
```

## 🎯 Severity Levels

- **BLOCKER** - Blocks further testing
- **CRITICAL** - Critical functionality
- **MAJOR** - Major functionality  
- **MINOR** - Minor functionality
- **TRIVIAL** - Low priority

## 💡 Pro Tips

1. **Use descriptive step names** - Makes reports easier to understand
2. **Attach screenshots** - Visual evidence is powerful
3. **Link to issues** - Connect tests to bug tracking (JIRA, Azure Boards)
4. **Set correct severity** - Helps prioritize failures
5. **Organize by Epic/Feature** - Logical test structure
6. **Check history** - Monitor trends over time

## 🔗 Links

- **Allure Documentation**: https://docs.qameta.io/allure/
- **GitHub**: https://github.com/allure-framework/allure2
- **Report Examples**: https://demo.qameta.io/allure/
- **Downloads**: https://github.com/allure-framework/allure2/releases

## ⚡ Quick Reference

| Task | Command |
|------|---------|
| Run & view report | `mvn clean test && allure serve allure-results/` |
| Just run tests | `mvn clean test` |
| Generate report | `allure generate allure-results/ --clean` |
| View report | `allure open allure-report/` |
| Check version | `allure --version` |
| Help | `allure --help` |

## 🐛 Troubleshooting

### "allure command not found"
- Install Allure properly from: https://github.com/allure-framework/allure2/releases
- Add to PATH environment variable

### Empty report
- Ensure tests ran (check for `allure-results/` with JSON files)
- Run: `mvn clean test` first

### No screenshots in report
- Use `AllureReporting.attachScreenshot()` in your tests
- Check that tests are actually attaching screenshots

### Report shows old results
- Delete `allure-report/` directory
- Run: `allure generate allure-results/ --clean`

---

**Status**: ✅ Allure Report fully integrated  
**Version**: 2.25.0  
**Next Step**: Run `mvn clean test && allure serve allure-results/`
