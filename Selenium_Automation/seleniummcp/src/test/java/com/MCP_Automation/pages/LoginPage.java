package com.MCP_Automation.pages;

import com.MCP_Automation.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Login Page Object Model
 * Contains all elements and methods related to login page
 */
public class LoginPage extends BasePage {
    
    // Web Elements using @FindBy annotation
    @FindBy(name = "username")
    private WebElement usernameField;
    
    @FindBy(name = "password")
    private WebElement passwordField;
    
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;
    
    @FindBy(xpath = "//h6[contains(text(), 'Dashboard')]")
    private WebElement dashboardElement;
    
    private static final String LOGIN_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    
    /**
     * Constructor
     * @param driver - WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Navigate to login page
     */
    public void navigateToLoginPage() {
        navigateTo(LOGIN_URL);
        log("Navigated to OrangeHRM login page");
    }
    
    /**
     * Enter username
     * @param username - Username to enter
     */
    public void enterUsername(String username) {
        clearAndEnterText(usernameField, username);
        log("Entered username: " + username);
    }
    
    /**
     * Enter password
     * @param password - Password to enter
     */
    public void enterPassword(String password) {
        clearAndEnterText(passwordField, password);
        log("Entered password");
    }
    
    /**
     * Click login button
     */
    public void clickLoginButton() {
        clickElement(loginButton);
        log("Clicked login button");
    }
    
    /**
     * Verify dashboard is displayed after login
     * @return - true if dashboard is visible, false otherwise
     */
    public boolean isDashboardDisplayed() {
        try {
            waitForElementToBeVisible(dashboardElement);
            log("Dashboard is displayed - Login successful");
            return true;
        } catch (Exception e) {
            log("Dashboard not found - Login may have failed");
            return false;
        }
    }
    
    /**
     * Complete login process
     * @param username - Username
     * @param password - Password
     * @return - true if login successful, false otherwise
     */
    public boolean login(String username, String password) {
        try {
            navigateToLoginPage();
            enterUsername(username);
            enterPassword(password);
            clickLoginButton();
            return isDashboardDisplayed();
        } catch (Exception e) {
            log("Login failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get page title
     * @return - Current page title
     */
    public String getLoginPageTitle() {
        return getPageTitle();
    }
    
    /**
     * Get current URL
     * @return - Current URL
     */
    public String getLoginPageUrl() {
        return getCurrentUrl();
    }
    
    /**
     * Log messages
     * @param message - Message to log
     */
    private void log(String message) {
        System.out.println("[LoginPage] " + message);
    }
}
