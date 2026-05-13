package com.MCP_Automation.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import com.MCP_Automation.config.TestConfig;

/**
 * Driver Factory for creating WebDriver instances
 */
public class DriverFactory {
    
    /**
     * Create WebDriver based on browser type
     * @param browserType - Type of browser (chrome, firefox, edge)
     * @return - WebDriver instance
     */
    public static WebDriver createDriver(String browserType) {
        WebDriver driver;
        
        switch (browserType.toLowerCase()) {
            case "chrome":
                driver = createChromeDriver();
                break;
            case "firefox":
                driver = createFirefoxDriver();
                break;
            case "edge":
                driver = createEdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
        
        // Set timeouts
        driver.manage().timeouts().implicitlyWait(
            java.time.Duration.ofSeconds(TestConfig.IMPLICIT_WAIT_TIMEOUT)
        );
        driver.manage().timeouts().pageLoadTimeout(
            java.time.Duration.ofSeconds(TestConfig.PAGE_LOAD_TIMEOUT)
        );
        
        return driver;
    }
    
    /**
     * Create Chrome WebDriver
     * @return - Chrome WebDriver instance
     */
    private static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        
        if (TestConfig.HEADLESS_MODE) {
            options.addArguments("--headless");
            options.addArguments("--window-size=" + TestConfig.BROWSER_WIDTH + "," + TestConfig.BROWSER_HEIGHT);
        }
        
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--start-maximized");
        
        return new ChromeDriver(options);
    }
    
    /**
     * Create Firefox WebDriver
     * @return - Firefox WebDriver instance
     */
    private static WebDriver createFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        
        if (TestConfig.HEADLESS_MODE) {
            options.addArguments("--headless");
            options.addArguments("--width=" + TestConfig.BROWSER_WIDTH);
            options.addArguments("--height=" + TestConfig.BROWSER_HEIGHT);
        }
        
        return new FirefoxDriver(options);
    }
    
    /**
     * Create Edge WebDriver
     * @return - Edge WebDriver instance
     */
    private static WebDriver createEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        
        if (TestConfig.HEADLESS_MODE) {
            options.addArguments("--headless");
            options.addArguments("--window-size=" + TestConfig.BROWSER_WIDTH + "," + TestConfig.BROWSER_HEIGHT);
        }
        
        options.addArguments("--disable-gpu");
        options.addArguments("--start-maximized");
        
        return new EdgeDriver(options);
    }
}
