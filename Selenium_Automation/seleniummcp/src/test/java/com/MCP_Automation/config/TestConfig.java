package com.MCP_Automation.config;

/**
 * Configuration class for managing test data and constants
 */
public class TestConfig {
    
    // Test Data
    public static final String VALID_USERNAME = "Admin";
    public static final String VALID_PASSWORD = "admin123";
    public static final String INVALID_USERNAME = "InvalidUser";
    public static final String INVALID_PASSWORD = "wrongpassword";
    
    // URLs
    public static final String BASE_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    public static final String DASHBOARD_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
    
    // Timeouts (in seconds)
    public static final int EXPLICIT_WAIT_TIMEOUT = 10;
    public static final int IMPLICIT_WAIT_TIMEOUT = 5;
    public static final int PAGE_LOAD_TIMEOUT = 20;
    
    // Browser Configuration
    public static final String BROWSER_TYPE = "chrome";
    public static final boolean HEADLESS_MODE = false;
    public static final boolean MAXIMIZE_WINDOW = true;
    
    // Report Configuration
    public static final String REPORT_DIR = "test-reports/";
    public static final String SCREENSHOT_DIR = "screenshots/";
    
    // Browser Window Size (for headless mode)
    public static final int BROWSER_WIDTH = 1920;
    public static final int BROWSER_HEIGHT = 1080;
    
    /**
     * Get environment variable or default value
     * @param key - Environment variable key
     * @param defaultValue - Default value if not found
     * @return - Environment value or default
     */
    public static String getProperty(String key, String defaultValue) {
        String value = System.getProperty(key);
        return value != null ? value : defaultValue;
    }
    
    /**
     * Get environment variable as boolean
     * @param key - Environment variable key
     * @param defaultValue - Default value if not found
     * @return - Boolean value
     */
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = System.getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }
}
