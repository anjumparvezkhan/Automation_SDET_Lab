import { test as base, expect } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage.js';
import { DashboardPage } from '../pages/DashboardPage.js';
import {PIMPage} from '../pages/PIMPage.js';

type Fixtures = {
  authenticatedPage: import('@playwright/test').Page;
  dashboardPage: DashboardPage;
  pimPage : PIMPage;
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

  pimPage: async({authenticatedPage}, use)=>{
    const pimPage = new PIMPage(authenticatedPage);

    await use(pimPage);
  }
});

export { expect };
