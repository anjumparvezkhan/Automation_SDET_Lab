import { test as base, expect } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage.js';
import { DashboardPage } from '../pages/DashboardPage.js';

type Fixtures = {
  authenticatedPage: import('@playwright/test').Page;
  dashboardPage: DashboardPage;
};

export const test = base.extend<Fixtures>({
  authenticatedPage: async ({ page }, use) => {
    const loginPage = new LoginPage(page);

    await page.goto('/', { waitUntil: 'domcontentloaded' });
    await loginPage.login('Admin', 'admin123');

    await expect(page).toHaveURL(/dashboard/);

    await use(page);
  },

  dashboardPage: async ({ authenticatedPage }, use) => {
    const dashboardPage = new DashboardPage(authenticatedPage);

    await use(dashboardPage);
  },
});

export { expect };
