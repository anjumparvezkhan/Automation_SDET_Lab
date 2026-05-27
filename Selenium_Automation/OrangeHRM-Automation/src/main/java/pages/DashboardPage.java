package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

    WebDriver driver;
    WebDriverWait wait;  // Instance variable for WebDriverWait

    // Dashboard WebElements
    @FindBy(xpath = "//span[@class='oxd-userdropdown-tab']")
    private WebElement userDropdownTab;
    
    @FindBy(xpath = "//a[normalize-space()='Logout']")
    private WebElement logoutLink;
    
    @FindBy(xpath = "//h6[contains(@class,'oxd-topbar-header-breadcrumb-module')]")
    private WebElement pageHeader;
    
    @FindBy(xpath = "//input[@placeholder='Search']")
    private WebElement searchBox;
    
    @FindBy(xpath = "//img[@alt='profile picture']")
    private WebElement profileImage;
    
    @FindBy(xpath = "//span[@class='oxd-icon bi-bell']")
    private WebElement notificationBell;
    
    @FindBy(xpath = "//div[@class='oxd-topbar-body']")
    private WebElement topBar;
    
    @FindBy(xpath = "//nav[@class='oxd-navbar-nav']")
    private WebElement navigationBar;
    
    @FindBy(xpath = "//div[@class='oxd-grid-4']")
    private WebElement quickLaunchContainer;
    
    @FindBy(xpath = "//div[@class='oxd-topbar-welcome']")
    private WebElement welcomeMessage;
    
    @FindBy(xpath = "//button[@type='button']//i[@class='oxd-icon bi-grid-3x2-gap']")
    private WebElement menuIcon;
    
    @FindBy(xpath = "//div[@class='oxd-layout-context']")
    private WebElement dashboardMainContent;
    
    @FindBy(xpath = "//div[@class='oxd-container']")
    private WebElement mainContainer;
    
    @FindBy(xpath = "//button[contains(@class, 'oxd-button')]")
    private WebElement actionButton;
    
    @FindBy(xpath = "//div[@class='oxd-layout-footer']")
    private WebElement footerElement;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
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
    
    // New methods for Dashboard WebElements
    
    public boolean isUserDropdownTabDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(userDropdownTab)).isDisplayed();
        } catch (NoSuchElementException e) {
            System.err.println("User dropdown tab not found: " + e.getMessage());
            return false;
        }
    }
    
    public String getPageHeaderText() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(pageHeader)).getText();
        } catch (NoSuchElementException e) {
            System.err.println("Page header not found: " + e.getMessage());
            return null;
        }
    }
    
    public void searchInDashboard(String searchTerm) {
        try {
            WebElement search = wait.until(ExpectedConditions.visibilityOf(searchBox));
            search.clear();
            search.sendKeys(searchTerm);
        } catch (NoSuchElementException e) {
            System.err.println("Search box not found: " + e.getMessage());
        }
    }
    
    public boolean isProfileImageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(profileImage)).isDisplayed();
        } catch (NoSuchElementException e) {
            System.err.println("Profile image not found: " + e.getMessage());
            return false;
        }
    }
    
    public void clickNotificationBell() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(notificationBell)).click();
        } catch (NoSuchElementException e) {
            System.err.println("Notification bell not found: " + e.getMessage());
        }
    }
    
    public boolean isTopBarDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(topBar)).isDisplayed();
        } catch (NoSuchElementException e) {
            System.err.println("Top bar not found: " + e.getMessage());
            return false;
        }
    }
    
    public boolean isNavigationBarDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(navigationBar)).isDisplayed();
        } catch (NoSuchElementException e) {
            System.err.println("Navigation bar not found: " + e.getMessage());
            return false;
        }
    }
    
    public String getWelcomeMessageText() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(welcomeMessage)).getText();
        } catch (NoSuchElementException e) {
            System.err.println("Welcome message not found: " + e.getMessage());
            return null;
        }
    }
    
    public void clickMenuIcon() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(menuIcon)).click();
        } catch (NoSuchElementException e) {
            System.err.println("Menu icon not found: " + e.getMessage());
        }
    }
    
    public boolean isDashboardMainContentDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(dashboardMainContent)).isDisplayed();
        } catch (NoSuchElementException e) {
            System.err.println("Dashboard main content not found: " + e.getMessage());
            return false;
        }
    }
    
    public void clickActionButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(actionButton)).click();
        } catch (NoSuchElementException e) {
            System.err.println("Action button not found: " + e.getMessage());
        }
    }
    
    public boolean isFooterDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(footerElement)).isDisplayed();
        } catch (NoSuchElementException e) {
            System.err.println("Footer not found: " + e.getMessage());
            return false;
        }
    }
    
    public boolean isDashboardLoadedSuccessfully() {
        try {
            return isTopBarDisplayed() && isNavigationBarDisplayed() && isDashboardMainContentDisplayed();
        } catch (Exception e) {
            System.err.println("Error checking if dashboard is loaded: " + e.getMessage());
            return false;
        }
    }

    
}

