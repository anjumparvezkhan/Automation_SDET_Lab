package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	WebDriver driver;
    WebDriverWait wait;  

    public LoginPage(WebDriver driver) {
        this.driver = driver; 
        PageFactory.initElements(driver, this);
    }

    private WebElement findElement(By by) {
    	wait = new WebDriverWait(this.driver, Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void login(String user, String pass) {
        findElement(By.name("username")).sendKeys(user);
        findElement(By.name("password")).sendKeys(pass);
        findElement(By.cssSelector("button[type='submit']")).click();
    }

    public String getErrorMsg() {
        return findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")).getText();
    }

    public String getUsernameFieldError() {
        return findElement(By.xpath("(//span[text()='Mandatory' or text()='Required'])[1]")).getText();
    }

    public String getPasswordFieldError() {
        return findElement(By.xpath("(//span[text()='Mandatory' or text()='Required'])[2]")).getText();
    }
}
