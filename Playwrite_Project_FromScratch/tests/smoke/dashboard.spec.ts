import { test, expect } from '../../src/fixtures/test.js';

test.describe('Dashboard Tests', () => {
  test('Verify Dashboard Sidebar is visible', async ({ dashboardPage }) => {
    await dashboardPage.verifyPageheader();
    await dashboardPage.isSidebarVisible();
    await dashboardPage.isAdminMenuClickable();
    await dashboardPage.isPimMenuClickable();
    await dashboardPage.isMyInfoMenuClickable();
  });

  test('Navigate to My Info', async ({ dashboardPage }) => {
    await dashboardPage.verifyPageheader();
    await dashboardPage.clickMyInfoMenu();
    await expect(dashboardPage.page).toHaveURL(/viewPersonalDetails/);
    await dashboardPage.verifyMyInfoFormHeading();
  });
});
