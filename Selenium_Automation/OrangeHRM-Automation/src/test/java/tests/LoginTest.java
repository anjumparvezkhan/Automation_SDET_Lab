package tests;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.TestListener;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ExcelReader;

@Listeners(TestListener.class)
public class LoginTest {

	String expectedTitle = "OrangeHRM";
	String expectedUrlContains = "dashboard";

	@Test(priority = 1, dataProvider = "loginData")
	public void testValidLogin(String username, String password, String status) {
		WebDriver driver = TestListener.getDriver();
		try {
			TestListener.getTest().info("Test started with Username: " + username + ", Status: " + status);
			
			LoginPage lp = new LoginPage(driver);
			TestListener.getTest().info("LoginPage initialized");
			
			

			if(ConfigReader.get("TestGroup").equals("sanity") || ConfigReader.get("TestGroup").equals("smoke")) {

				lp.login(username, password);
				TestListener.getTest().info("Login action performed");

				TestListener.getTest().info("Executing Login test case...");
				// 1. Verify the Page Title
				TestListener.getTest().info("Verifying page title...");
				Assert.assertEquals(driver.getTitle(), expectedTitle, "Title mismatch!");
				TestListener.getTest().pass("Page title verified: " + driver.getTitle());

				// 2. Verify the URL contains "dashboard"
				TestListener.getTest().info("Verifying URL contains 'dashboard'...");
				Assert.assertTrue(driver.getCurrentUrl().contains(expectedUrlContains),
						"URL =>" + driver.getCurrentUrl() + " does not contain expected string!");
				TestListener.getTest().pass("URL verified: " + driver.getCurrentUrl());
			
			} else {
				
				if(status.equals("Success")) {
					
					
					// 1. Verify the Page Title
					TestListener.getTest().info("Verifying page title...");
					Assert.assertEquals(driver.getTitle(), expectedTitle, "Title mismatch!");
					TestListener.getTest().pass("Page title verified: " + driver.getTitle());

					// 2. Verify the URL contains "dashboard"
					TestListener.getTest().info("Verifying URL contains 'dashboard'...");
					Assert.assertTrue(driver.getCurrentUrl().contains(expectedUrlContains),
							"URL =>" + driver.getCurrentUrl() + " does not contain expected string!");
					TestListener.getTest().pass("URL verified: " + driver.getCurrentUrl());

					DashboardPage dashboardPage = new DashboardPage(driver);
					TestListener.getTest().info("Performing logout...");
					dashboardPage.UserDropdown();
					dashboardPage.Logout();
					TestListener.getTest().pass("Successfully logged out");
					
				} else if(status.equals("Empty")) {
					TestListener.getTest().info("Verifying error messages for empty fields...");
					
					String UsernameError = lp.getUsernameFieldError();
					String PasswordError = lp.getPasswordFieldError();
					TestListener.getTest().info("Username Error: " + UsernameError);
					TestListener.getTest().info("Password Error: " + PasswordError);
					
					Assert.assertTrue(
							UsernameError.contains("Required") || UsernameError.contains("Mandatory"),
							"Text does not contain Required or Mandatory"
						);
					TestListener.getTest().pass("✓ Username field error verified");
					
					Assert.assertTrue(
							PasswordError.contains("Required") || PasswordError.contains("Mandatory"),
							"Text does not contain Required or Mandatory"
						);
					TestListener.getTest().pass("✓ Password field error verified");

				} else {
					TestListener.getTest().info("Verifying invalid credentials error message...");
					
					String expectedResult = "Invalid credentials";
					String actualString = lp.getErrorMsg();
					TestListener.getTest().info("Expected: " + expectedResult + ", Actual: " + actualString);
					
					Assert.assertEquals(expectedResult, actualString);
					TestListener.getTest().pass("✓ Invalid credentials error verified");
				}
			}	
		} catch (Exception e) {
			TestListener.getTest().fail("Test failed with exception: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@DataProvider(name = "loginData")
	public Object[][] getLoginData() throws Exception {
		try {
			String dataPath = ConfigReader.get("DataRepositoryPath");
			System.out.println("Data file path: " + dataPath);
			
			String sheetName = ConfigReader.get("SheetName");
			System.out.println("Sheet name: " + sheetName);


			ExcelReader er = new ExcelReader(dataPath, sheetName);
			List<String[]> rows = er.logingetAllRows();
			
			if(rows == null || rows.isEmpty()) {
				throw new Exception("No test data found in Excel file");
			}

			Object[][] data;

			if(ConfigReader.get("TestGroup").equals("sanity") || ConfigReader.get("TestGroup").equals("smoke")){
				
				data = new Object[1][];
				data[0] = rows.get(0); // Load only the first row for sanity/smoke tests
				
			}else if(ConfigReader.get("TestGroup").equals("regression")) {
				
				data = new Object[rows.size()][];
				for (int i = 0; i < rows.size(); i++) {
					data[i] = rows.get(i);
				}
			}  else {
            throw new Exception("Unknown TestGroup: " + ConfigReader.get("TestGroup"));
        	}
			System.out.println("Total test cases loaded: " + data.length);
			return data;
			
		} catch (Exception e) {
			System.out.println("Error loading test data: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
}
