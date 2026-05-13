package com.MCP_Automation;

import com.MCP_Automation.pages.LoginPage;
import com.MCP_Automation.reporting.TestReport;
import com.MCP_Automation.reporting.AllureReporting;
import com.MCP_Automation.utils.SeleniumUtil;
import com.MCP_Automation.config.TestConfig;
import com.MCP_Automation.factory.DriverFactory;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.WebDriver;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Login Test using Page Object Model with Allure Reporting
 * Uses LoginPage POM, BasePage base class, DriverFactory, TestConfig, and Allure Reports
 */
@Epic("Authentication")
@Feature("User Login")
@Story("Valid user login functionality")
@DisplayName("OrangeHRM Login Tests")
public class Login {
    
    private WebDriver driver;
    private LoginPage loginPage;
    private TestReport report;
    
    @BeforeEach
    public void setUp() {
        Allure.step("Initialize WebDriver and test setup");
        
        // Initialize WebDriver using DriverFactory
        driver = DriverFactory.createDriver(TestConfig.BROWSER_TYPE);
        
        // Maximize window if configured
        if (TestConfig.MAXIMIZE_WINDOW) {
            driver.manage().window().maximize();
        }
        
        // Initialize Page Object
        loginPage = new LoginPage(driver);
        
        // Initialize Test Report (for backward compatibility)
        report = new TestReport("OrangeHRM_Login_Test_Allure");
        
        report.logInfo("Setup", "Browser initialized and maximized");
        AllureReporting.step("Browser Setup", "WebDriver initialized with " + TestConfig.BROWSER_TYPE + " browser");
    }
    
    @Test
    @DisplayName("Verify user can login with valid credentials")
    @Description("Test to verify that a user can successfully login to OrangeHRM with valid username and password")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("QA Team")
    public void testOrangeHRMLogin() {
        try {
            // Step 1: Navigate to login page
            Allure.step("Navigate to Login Page", () -> {
                report.logInfo("Step 1", "Navigating to OrangeHRM login page");
                loginPage.navigateToLoginPage();
                AllureReporting.step("Navigation", "Successfully navigated to " + TestConfig.BASE_URL);
            });
            
            // Step 2: Enter username
            Allure.step("Enter Username", () -> {
                report.logInfo("Step 2", "Entering username: " + TestConfig.VALID_USERNAME);
                loginPage.enterUsername(TestConfig.VALID_USERNAME);
                report.logPass("Username Entry", "Username entered successfully");
                AllureReporting.step("Username Input", "Username '" + TestConfig.VALID_USERNAME + "' entered in username field");
            });
            
            // Step 3: Enter password
            Allure.step("Enter Password", () -> {
                report.logInfo("Step 3", "Entering password");
                loginPage.enterPassword(TestConfig.VALID_PASSWORD);
                report.logPass("Password Entry", "Password entered successfully");
                AllureReporting.step("Password Input", "Password entered in password field");
            });
            
            // Step 4: Click login button
            Allure.step("Click Login Button", () -> {
                report.logInfo("Step 4", "Clicking login button");
                loginPage.clickLoginButton();
                report.logPass("Login Button Click", "Login button clicked successfully");
                AllureReporting.step("Button Click", "Login button clicked successfully");
            });
            
            // Step 5: Verify dashboard is displayed
            Allure.step("Verify Dashboard", () -> {
                report.logInfo("Step 5", "Verifying dashboard is displayed");
                boolean isDashboardDisplayed = loginPage.isDashboardDisplayed();
                assertTrue(isDashboardDisplayed, "Dashboard should be displayed after successful login");
                report.logPass("Dashboard Verification", "Dashboard displayed successfully - Login successful!");
                AllureReporting.step("Verification", "Dashboard element found and verified - Login successful");
                AllureReporting.attachScreenshot(driver, "Dashboard_Success");
            });
            
            // Step 6: Verify page details
            Allure.step("Verify Page Details", () -> {
                String pageTitle = loginPage.getLoginPageTitle();
                String currentUrl = loginPage.getLoginPageUrl();
                report.logInfo("Page Details", "Page Title: " + pageTitle + " | URL: " + currentUrl);
                report.logPass("Login Test", "OrangeHRM login test completed successfully");
                AllureReporting.attachText("Page Details", "Title: " + pageTitle + "\nURL: " + currentUrl);
                AllureReporting.step("Test Complete", "All verifications passed - Login test successful");
            });
            
        } catch (AssertionError e) {
            report.logFail("Login Verification", "Login verification failed: " + e.getMessage());
            SeleniumUtil.takeScreenshot(driver, "login_test_failed");
            AllureReporting.attachScreenshot(driver, "Test_Failed_Screenshot");
            AllureReporting.attachPageSource(driver);
            AllureReporting.step("Test Failure", "Assertion failed: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            report.logFail("Login Test", "Login test failed with error: " + e.getMessage());
            SeleniumUtil.takeScreenshot(driver, "login_test_error");
            AllureReporting.attachScreenshot(driver, "Test_Error_Screenshot");
            AllureReporting.attachPageSource(driver);
            AllureReporting.step("Test Error", "Exception occurred: " + e.getMessage());
            fail("Login test failed: " + e.getMessage());
        }
    }
    
    @AfterEach
    public void tearDown() {
        try {
            Allure.step("Teardown", () -> {
                // Generate reports
                report.generateHTMLReport();
                report.generateTextReport();
                
                // Close browser
                if (driver != null) {
                    report.logInfo("Teardown", "Closing browser");
                    driver.quit();
                }
                
                // Print summary
                System.out.println("\n========================================");
                System.out.println("Test Summary:");
                System.out.println("Passed Steps: " + report.getPassCount());
                System.out.println("Failed Steps: " + report.getFailCount());
                System.out.println("Report generated in: test-reports/");
                System.out.println("Allure Report: Run 'allure serve allure-results'");
                System.out.println("========================================\n");
            });
            
        } catch (Exception e) {
            System.err.println("Error during teardown: " + e.getMessage());
            AllureReporting.step("Teardown Error", e.getMessage());
        }
    }
}
