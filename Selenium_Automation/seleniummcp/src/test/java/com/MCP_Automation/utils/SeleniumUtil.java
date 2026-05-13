package com.MCP_Automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for Selenium WebDriver operations
 */
public class SeleniumUtil {
    
    /**
     * Take screenshot of current page
     * @param driver - WebDriver instance
     * @param screenshotName - Name of the screenshot
     * @return - Path to the screenshot file
     */
    public static String takeScreenshot(WebDriver driver, String screenshotName) {
        String timestamp = getTimestamp();
        String screenshotDir = "screenshots/";
        
        // Create screenshots directory if it doesn't exist
        new File(screenshotDir).mkdirs();
        
        String screenshotPath = screenshotDir + screenshotName + "_" + timestamp + ".png";
        
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);
            File destination = new File(screenshotPath);
            Files.copy(source.toPath(), destination.toPath());
            System.out.println("Screenshot saved at: " + screenshotPath);
            return screenshotPath;
        } catch (IOException e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get current timestamp in yyyy-MM-dd_HH-mm-ss format
     * @return - Formatted timestamp
     */
    public static String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }
    
    /**
     * Get current timestamp in yyyy-MM-dd HH:mm:ss format
     * @return - Formatted timestamp
     */
    public static String getFormattedTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    
    /**
     * Wait for specified milliseconds
     * @param milliseconds - Time to wait in milliseconds
     */
    public static void waitForMilliseconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Wait interrupted: " + e.getMessage());
        }
    }
    
    /**
     * Wait for specified seconds
     * @param seconds - Time to wait in seconds
     */
    public static void waitForSeconds(int seconds) {
        waitForMilliseconds(seconds * 1000L);
    }
    
    /**
     * Scroll to element
     * @param driver - WebDriver instance
     * @param yOffset - Y offset to scroll
     */
    public static void scrollPage(WebDriver driver, int yOffset) {
        String script = "window.scrollBy(0, " + yOffset + ");";
        executeJavaScript(driver, script);
    }
    
    /**
     * Execute JavaScript
     * @param driver - WebDriver instance
     * @param script - JavaScript code to execute
     * @return - Result of the script execution
     */
    public static Object executeJavaScript(WebDriver driver, String script) {
        try {
            return ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(script);
        } catch (Exception e) {
            System.err.println("Failed to execute JavaScript: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get page source
     * @param driver - WebDriver instance
     * @return - Page source code
     */
    public static String getPageSource(WebDriver driver) {
        return driver.getPageSource();
    }
    
    /**
     * Print test log message
     * @param message - Message to print
     */
    public static void log(String message) {
        System.out.println("[" + getFormattedTimestamp() + "] " + message);
    }
    
    /**
     * Print error log message
     * @param message - Message to print
     */
    public static void errorLog(String message) {
        System.err.println("[" + getFormattedTimestamp() + "] ERROR: " + message);
    }
}
