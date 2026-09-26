import { expect, Locator, Page } from '@playwright/test';

export class DashboardPage {
  readonly page: Page;
  readonly pageheading: Locator;
  readonly dashboardSidebar: Locator;
  readonly myInfoMenu: Locator;
  readonly pimMenu: Locator;
  readonly adminMenu: Locator;
  readonly formheading: Locator;

  constructor(page: Page) {
    this.page = page;
    this.dashboardSidebar = page.getByRole('navigation', { name: 'Sidepanel' });
    this.pageheading = page.getByRole('heading', { name: 'Dashboard' });
    this.myInfoMenu = page.getByRole('link', { name: 'My Info' });
    this.pimMenu = page.getByRole('link', { name: 'PIM' });
    this.adminMenu = page.getByRole('link', { name: 'Admin' });
    this.formheading = page.getByRole('heading', { name: 'Personal Details' });
  }

  async verifyPageheader() {
    await expect(this.pageheading).toHaveText('Dashboard');
  }

  async isSidebarVisible(): Promise<boolean> {
    return await expect(this.dashboardSidebar)
      .toBeVisible({ timeout: 5000 })
      .then(() => true)
      .catch(() => false);
  }

  async isAdminMenuClickable(): Promise<boolean> {
    return await expect(this.adminMenu)
      .toBeEnabled({ timeout: 5000 })
      .then(() => true)
      .catch(() => false);
  }

  async isPimMenuClickable(): Promise<boolean> {
    return await expect(this.pimMenu)
      .toBeEnabled({ timeout: 5000 })
      .then(() => true)
      .catch(() => false);
  }

  async isMyInfoMenuClickable(): Promise<boolean> {
    return await expect(this.myInfoMenu)
      .toBeEnabled({ timeout: 5000 })
      .then(() => true)
      .catch(() => false);
  }

  async clickMyInfoMenu() {
    await this.myInfoMenu.click();
  }

  async verifyMyInfoFormHeading() {
    await expect(this.formheading).toHaveText('Personal Details');
  }

  async clickPimMenu(){
    await this.pimMenu.click();
  }
}
