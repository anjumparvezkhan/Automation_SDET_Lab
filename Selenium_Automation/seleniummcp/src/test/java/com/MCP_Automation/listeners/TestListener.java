package com.MCP_Automation.listeners;

import com.MCP_Automation.utils.SeleniumUtil;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;
import java.util.Optional;

/**
 * Test Listener for handling test execution events
 * Implements JUnit 5 TestWatcher extension
 */
public class TestListener implements TestWatcher {
    
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        SeleniumUtil.log("Test Disabled: " + context.getDisplayName());
    }
    
    @Override
    public void testSuccessful(ExtensionContext context) {
        SeleniumUtil.log("✓ Test Passed: " + context.getDisplayName());
    }
    
    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        SeleniumUtil.log("Test Aborted: " + context.getDisplayName());
        SeleniumUtil.errorLog("Abort Reason: " + cause.getMessage());
    }
    
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        SeleniumUtil.errorLog("✗ Test Failed: " + context.getDisplayName());
        SeleniumUtil.errorLog("Failure Reason: " + cause.getMessage());
    }
}
