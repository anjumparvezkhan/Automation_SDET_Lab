package com.MCP_Automation.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

/**
 * Base Page class containing common Selenium operations
 * All page objects should extend this class
 */
public class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final int TIMEOUT_SECONDS = 10;
    
    /**
     * Constructor - initializes WebDriver and WebDriverWait
     * @param driver - WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Wait for an element to be visible
     * @param element - WebElement to wait for
     * @return - WebElement once visible
     */
    protected WebElement waitForElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    /**
     * Wait for an element to be clickable
     * @param element - WebElement to wait for
     * @return - WebElement once clickable
     */
    protected WebElement waitForElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    /**
     * Clear and enter text into an element
     * @param element - WebElement to enter text into
     * @param text - Text to enter
     */
    protected void clearAndEnterText(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Click on an element with wait
     * @param element - WebElement to click
     */
    protected void clickElement(WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
    }
    
    /**
     * Get current page title
     * @return - Page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Get current URL
     * @return - Current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    /**
     * Get text from an element
     * @param element - WebElement to get text from
     * @return - Element text
     */
    protected String getElementText(WebElement element) {
        waitForElementToBeVisible(element);
        return element.getText();
    }
    
    /**
     * Navigate to a URL
     * @param url - URL to navigate to
     */
    protected void navigateTo(String url) {
        driver.get(url);
    }
    
    /**
     * Maximize browser window
     */
    protected void maximizeWindow() {
        driver.manage().window().maximize();
    }
}
