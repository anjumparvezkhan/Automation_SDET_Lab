package base;

import java.lang.reflect.Method;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import driver.DriverFactory;
import utils.ConfigReader;
import utils.ExtentManager;

public class BaseTest {
    protected WebDriver driver;
    protected ExtentReports extent;
    protected ExtentTest test;
    
    @BeforeSuite
    public void setupReport() {
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void beforeTest(Method method) {
        // Initialize driver first
        driver = DriverFactory.initDriver();
        driver.get(ConfigReader.get("url"));
        
        // Then create test report
        test = extent.createTest(method.getName());
    }

    @AfterMethod
    public void afterTest(ITestResult result) {
        // Capture test result
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                test.pass("Test Passed");
                break;
            case ITestResult.FAILURE:
                test.fail(result.getThrowable());
                break;
            case ITestResult.SKIP:
                test.skip("Test Skipped");
                break;
        }
        
        // Then quit driver
        DriverFactory.quitDriver();
    }

    @AfterSuite
    public void tearDownReport() {
        extent.flush();
    }
}