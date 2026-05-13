# Allure Report Integration Guide

## Overview

**Allure Report** is a professional, feature-rich test reporting framework that provides:

- ✅ Beautiful interactive HTML reports
- ✅ Test categorization (Epic, Feature, Story)
- ✅ Severity levels for tests
- ✅ Screenshot and attachment support
- ✅ Step-by-step execution logs
- ✅ Test history and trends
- ✅ Timeline and duration analysis
- ✅ Defect and test case linking
- ✅ Custom behaviors and tags

## Installation & Setup

### 1. **Install Allure Command Line Tool**

**Windows (using Chocolatey):**
```bash
choco install allure
```

**Windows (Manual):**
- Download from: https://github.com/allure-framework/allure2/releases
- Extract to `C:\allure-commandline\bin`
- Add to PATH environment variable

**macOS (using Homebrew):**
```bash
brew install allure
```

**Linux (using apt):**
```bash
sudo apt-add-repository ppa:qameta/allure
sudo apt-get update
sudo apt-get install allure
```

### 2. **Verify Installation**
```bash
allure --version
# Should output: 2.25.0 or similar
```

### 3. **Maven is Already Configured**
The `pom.xml` has been updated with Allure dependencies and Maven plugin

## Project Structure

```
src/test/
├── java/com/MCP_Automation/
│   ├── reporting/
│   │   ├── AllureReporting.java ⭐ NEW
│   │   └── TestReport.java (still available)
│   └── Login.java (updated with @Epic, @Feature, @Story)
└── resources/
    └── allure.properties ⭐ NEW
```

## Running Tests with Allure Reports

### 1. **Run Tests and Generate Allure Results**
```bash
mvn clean test
```

This generates:
- `allure-results/` directory with test data (JSON files)
- Older `test-reports/` directory (custom HTML reports still work)
- `screenshots/` directory with failure screenshots

### 2. **Generate and Open Allure Report Locally**
```bash
# Generate and serve the Allure report (opens in browser)
allure serve allure-results/
```

This opens the report at: `http://localhost:4200`

### 3. **Generate Allure Report (without serving)**
```bash
# Generate HTML report in allure-report/ directory
allure generate allure-results/
```

### 4. **Open Generated Report**
```bash
# Open the report in browser
allure open allure-report/
```

## Using Allure Annotations in Tests

### Test Metadata Annotations

```java
@Epic("Authentication")           // High-level feature group
@Feature("User Login")            // Feature being tested
@Story("Valid user login")        // Specific story/scenario
@DisplayName("Test description")  // Readable test name
@Severity(SeverityLevel.CRITICAL) // Test importance
@Owner("Developer Name")          // Test owner
public void testOrangeHRMLogin() {
    // Test code
}
```

### Severity Levels

```
SeverityLevel.BLOCKER    - Most critical tests
SeverityLevel.CRITICAL   - Critical functionality
SeverityLevel.MAJOR      - Major functionality
SeverityLevel.MINOR      - Minor functionality
SeverityLevel.TRIVIAL    - Low priority
```

## Using AllureReporting Utility

### 1. **Add Step to Report**
```java
AllureReporting.step("Step Name", "Step Description");
```

### 2. **Attach Screenshot**
```java
AllureReporting.attachScreenshot(driver, "Screenshot Name");
```

### 3. **Attach Text Information**
```java
AllureReporting.attachText("Page Details", "Title: " + title + "\nURL: " + url);
```

### 4. **Attach Page Source**
```java
AllureReporting.attachPageSource(driver);
```

### 5. **Add Links**
```java
// Link to external resource
AllureReporting.addLink("Documentation", "https://example.com/docs");

// Link to issue tracker
AllureReporting.addIssueLink("BUG-123");

// Link to test management system
AllureReporting.addTestCaseLink("TC-456");
```

## Allure Report Features

### 1. **Overview Dashboard**
- Total tests, passed, failed, skipped
- Test execution time
- Pass rate percentage
- Recent test runs

### 2. **Test Results**
- Detailed view of each test
- Pass/fail status
- Execution duration
- Start and finish times
- Owner and severity

### 3. **Behaviors**
- Tests organized by Epic, Feature, Story
- Hierarchical view
- Execution status per behavior level

### 4. **Categories**
- Group tests by type (functional, smoke, regression)
- Custom categorization

### 5. **Timeline**
- Visual timeline of test execution
- Duration analysis
- Parallel execution view

### 6. **History**
- Track test results over time
- Trend analysis
- Success/failure patterns

## Advanced Usage

### Custom Test Configuration

Edit `src/test/resources/allure.properties`:

```properties
# Report name
allure.report.name=OrangeHRM Selenium Tests

# Link patterns for issue tracker
allure.issues.tracker.pattern=http://jira.example.com/browse/%s

# Link patterns for test management
allure.tests.management.pattern=http://testrail.example.com/index.php?/cases/view/%s
```

### Lambda Steps (Alternative Step Format)

```java
Allure.step("Step Description", () -> {
    // Step implementation
    loginPage.enterUsername("admin");
    // Automatically records pass/fail
});
```

### Nested Steps

```java
Allure.step("Parent Step", () -> {
    Allure.step("Child Step 1", () -> {
        // Child step logic
    });
    
    Allure.step("Child Step 2", () -> {
        // Child step logic
    });
});
```

## CI/CD Integration

### GitHub Actions

```yaml
- name: Run Tests
  run: mvn clean test

- name: Generate Allure Report
  if: always()
  run: allure generate allure-results --clean -o allure-report

- name: Upload Report
  uses: actions/upload-artifact@v2
  if: always()
  with:
    name: allure-report
    path: allure-report/
```

### Jenkins

```groovy
stage('Test') {
    steps {
        sh 'mvn clean test'
    }
}

stage('Report') {
    steps {
        allure([
            reportBuildPolicy: 'ALWAYS',
            results: [[path: 'allure-results']]
        ])
    }
}
```

## Report Output Locations

| Report | Location | Usage |
|--------|----------|-------|
| Allure Results | `allure-results/` | Raw test data (JSON) |
| Allure Report | `allure-report/` | Generated HTML report |
| Custom HTML Reports | `test-reports/*.html` | Custom reporting |
| Custom Text Reports | `test-reports/*.txt` | Text reports |
| Screenshots | `screenshots/` | Failure screenshots |

## Example Test with Full Allure Integration

```java
@Epic("Authentication")
@Feature("User Login")
@Story("Valid credentials login")
@DisplayName("Verify successful login")
@Severity(SeverityLevel.CRITICAL)
@Owner("QA Team")
public void testLoginFlow() {
    try {
        // Lambda step format
        Allure.step("Navigate to login page", () -> {
            loginPage.navigateToLoginPage();
            AllureReporting.attachScreenshot(driver, "Login_Page_Loaded");
        });
        
        Allure.step("Enter credentials", () -> {
            loginPage.enterUsername("admin");
            loginPage.enterPassword("password");
        });
        
        Allure.step("Click login button", () -> {
            loginPage.clickLoginButton();
        });
        
        Allure.step("Verify dashboard", () -> {
            assertTrue(loginPage.isDashboardDisplayed());
            AllureReporting.attachScreenshot(driver, "Dashboard_Success");
        });
        
        // Link to related items
        AllureReporting.addTestCaseLink("TC-123");
        AllureReporting.addIssueLink("BUG-456");
        
    } catch (Exception e) {
        AllureReporting.attachScreenshot(driver, "Test_Failure");
        AllureReporting.attachPageSource(driver);
        throw e;
    }
}
```

## Troubleshooting

### Issue: "allure: command not found"
**Solution**: Install Allure properly or add to PATH

### Issue: Empty Allure Report
**Solution**: Ensure tests ran successfully and `allure-results/` contains JSON files

### Issue: No Screenshots in Report
**Solution**: Use `AllureReporting.attachScreenshot()` before attachment

### Issue: Report doesn't reflect latest test run
**Solution**: Delete `allure-report/` and regenerate: `allure generate allure-results --clean`

## Useful Commands

```bash
# Run tests and generate Allure report
mvn clean test && allure serve allure-results/

# Generate report without serving
allure generate allure-results/ --clean -o allure-report/

# Open existing report
allure open allure-report/

# Clear results and regenerate
rm -rf allure-results/ allure-report/
mvn clean test
allure serve allure-results/

# View Allure version
allure --version

# Check Allure help
allure --help
```

## Best Practices

1. **Use meaningful Epic/Feature/Story** - Organize tests logically
2. **Set appropriate severity** - Helps prioritize failures
3. **Attach screenshots** - Visual evidence of test state
4. **Use steps** - Break down test into logical steps
5. **Link to issues** - Connect tests to bug tracking
6. **Clean up results** - Periodically clean old results
7. **Keep step descriptions clear** - Make reports readable
8. **Use custom tags** - For additional categorization
9. **Monitor trends** - Check history for patterns
10. **Share reports** - Generate and share with team

## Documentation Links

- [Allure Official Documentation](https://docs.qameta.io/allure/)
- [Allure GitHub](https://github.com/allure-framework/allure2)
- [Allure Releases](https://github.com/allure-framework/allure2/releases)
- [JUnit 5 Allure Integration](https://docs.qameta.io/allure/#_junit5)

---

**Allure Version**: 2.25.0  
**Integration Status**: ✅ Complete  
**Next Steps**: Run `mvn clean test && allure serve allure-results/`
