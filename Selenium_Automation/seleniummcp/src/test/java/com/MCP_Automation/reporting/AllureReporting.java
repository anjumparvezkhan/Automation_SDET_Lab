package com.MCP_Automation.reporting;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.ByteArrayInputStream;

/**
 * Allure Reporting Utility class
 * Provides helper methods for Allure Report integration
 */
public class AllureReporting {
    
    /**
     * Add step to Allure report
     * @param stepName - Name of the step
     * @param description - Description of the step
     */
    public static void step(String stepName, String description) {
        Allure.step(stepName + " - " + description);
    }
    
    /**
     * Attach screenshot to Allure report
     * @param driver - WebDriver instance
     * @param screenshotName - Name for the screenshot
     */
    public static void attachScreenshot(WebDriver driver, String screenshotName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(screenshotName, "image/png", new ByteArrayInputStream(screenshotBytes), ".png");
        } catch (Exception e) {
            System.err.println("Failed to attach screenshot: " + e.getMessage());
        }
    }
    
    /**
     * Attach text information to Allure report
     * @param name - Name of the attachment
     * @param text - Text content
     */
    public static void attachText(String name, String text) {
        Allure.addAttachment(name, "text/plain", text);
    }
    
    /**
     * Attach page source to Allure report
     * @param driver - WebDriver instance
     */
    public static void attachPageSource(WebDriver driver) {
        try {
            String pageSource = driver.getPageSource();
            Allure.addAttachment("Page Source", "text/html", pageSource);
        } catch (Exception e) {
            System.err.println("Failed to attach page source: " + e.getMessage());
        }
    }
    
    /**
     * Add link to Allure report
     * @param name - Link name
     * @param url - URL
     */
    public static void addLink(String name, String url) {
        Allure.link(name, url);
    }
    
    /**
     * Add issue link to Allure report
     * @param issueId - Issue ID
     */
    public static void addIssueLink(String issueId) {
        Allure.issue("BUG", issueId);
    }
    
    /**
     * Add test case link to Allure report
     * @param testCaseId - Test case ID
     */
    public static void addTestCaseLink(String testCaseId) {
        Allure.link("Test Case", testCaseId);
    }
}
