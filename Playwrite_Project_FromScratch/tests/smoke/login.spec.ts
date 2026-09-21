import { test, expect } from '@playwright/test';
import { LoginPage } from '../../src/pages/LoginPage.js';

test.describe('Login Tests', () => {
  let loginPage: LoginPage;

  test.beforeEach('Home page should have the correct title', async ({ page }) => {
    await page.goto('/',  { waitUntil: 'domcontentloaded' });
    const title = await page.title();
    expect(title).toBe('OrangeHRM');
  });
  
  test('basic login test', async ({page}) => {
    loginPage = new LoginPage(page);
    await loginPage.login('Admin', 'admin123');
    await expect(page).toHaveURL(/dashboard/);
  });

  test('login with invalid credentials', async ({page}) => {
    loginPage = new LoginPage(page);
    await loginPage.login('Admin', 'wrongpassword');
    await loginPage.verifyErrorMessage();
    const errorMessage = await loginPage.getLoginError();
    expect(errorMessage).toBe('Invalid credentials');
    await expect(page).toHaveURL(/login/); 

  });

});