package base;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import driver.DriverFactory;
import utils.ConfigReader;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setup(Method method) {

        driver = DriverFactory.initDriver();

        driver.get(ConfigReader.get("url"));
    }

    @AfterMethod
    public void teardown() {

        DriverFactory.quitDriver();
    }
}