package tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ExcelReader;

public class LoginTest extends BaseTest {

	@Test(dataProvider = "loginData")
	public void validLoginTest(String username, String password, String status) {
		
		try {
			test.info("Test started with Username: " + username + ", Status: " + status);
			
			LoginPage lp = new LoginPage(driver);
			test.info("LoginPage initialized");
			
			lp.login(username, password);
			test.info("Login action performed");
			
			if(status.equals("Success")) {
				String expectedTitle = "OrangeHRM";
				String expectedUrlContains = "dashboard";
				
				// 1. Verify the Page Title
				test.info("Verifying page title...");
				Assert.assertEquals(driver.getTitle(), expectedTitle, "Title mismatch!");
				test.pass("✓ Page title verified: " + driver.getTitle());

				// 2. Verify the URL contains "dashboard"
				test.info("Verifying URL contains 'dashboard'...");
				Assert.assertTrue(driver.getCurrentUrl().contains(expectedUrlContains),
						"URL =>" + driver.getCurrentUrl() + " does not contain expected string!");
				test.pass("✓ URL verified: " + driver.getCurrentUrl());

				DashboardPage dashboardPage = new DashboardPage(driver);
				test.info("Performing logout...");
				dashboardPage.UserDropdown();
				dashboardPage.Logout();
				test.pass("✓ Successfully logged out");
				
			} else if(status.equals("Empty")) {
				test.info("Verifying error messages for empty fields...");
				
				String UsernameError = lp.getUsernameFieldError();
				String PasswordError = lp.getPasswordFieldError();
				test.info("Username Error: " + UsernameError);
				test.info("Password Error: " + PasswordError);
				
				Assert.assertTrue(
						UsernameError.contains("Required") || UsernameError.contains("Mandatory"),
					    "Text does not contain Required or Mandatory"
					);
				test.pass("✓ Username field error verified");
				
				Assert.assertTrue(
						PasswordError.contains("Required") || PasswordError.contains("Mandatory"),
					    "Text does not contain Required or Mandatory"
					);
				test.pass("✓ Password field error verified");

			} else {
				test.info("Verifying invalid credentials error message...");
				
				String expectedResult = "Invalid credentials";
				String actualString = lp.getErrorMsg();
				test.info("Expected: " + expectedResult + ", Actual: " + actualString);
				
				Assert.assertEquals(expectedResult, actualString);
				test.pass("✓ Invalid credentials error verified");
			}
		} catch (Exception e) {
			test.fail("Test failed with exception: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@DataProvider(name = "loginData")
	public Object[][] getLoginData() throws Exception {
		try {
			String dataPath = ConfigReader.get("DataRepositoryPath");
			System.out.println("Data file path: " + dataPath);
			
			ExcelReader er = new ExcelReader(dataPath, "Login");
			List<String[]> rows = er.logingetAllRows();
			
			if(rows == null || rows.isEmpty()) {
				throw new Exception("No test data found in Excel file");
			}

			Object[][] data = new Object[rows.size()][];
			for (int i = 0; i < rows.size(); i++) {
				data[i] = rows.get(i);
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
