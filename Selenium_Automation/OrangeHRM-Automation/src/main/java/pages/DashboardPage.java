package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

    WebDriver driver;
    WebDriverWait wait;  // Instance variable for WebDriverWait

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));  // Initialize the WebDriverWait instance
    }

    private WebElement findElement(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void UserDropdown() {
        try {
            findElement(By.xpath("//span[@class='oxd-userdropdown-tab']")).click();;
           
        } catch (NoSuchElementException e) {
            System.err.println("One or more elements were not found: " + e.getMessage());
        }
    }
    
    public void Logout() {
        try {
            findElement(By.xpath("//a[normalize-space()='Logout']")).click();
            
        } catch (NoSuchElementException e) {
            System.err.println("One or more elements were not found: " + e.getMessage());
        }
    }
}

