import {test, expect} from '../../src/fixtures/test.js';
import { DashboardPage } from '../../src/pages/DashboarPage.js';

test.describe('Dashboard Tests', () => {

  let dashboardPage: DashboardPage;

  test('Verify Dashboard Sidebar is visible', async({authenticatedPage }) =>{
     
    dashboardPage = new DashboardPage(authenticatedPage);
    await dashboardPage.verifyPageheader();
    await dashboardPage.isSidebarVisible();
    await dashboardPage.isAdminMenuClickable();
    await dashboardPage.isPimMenuClickable();
    await dashboardPage.isMyInfoMenuClickable();
  })

  test('Navigate to My Info', async({authenticatedPage}) => {

    dashboardPage = new DashboardPage(authenticatedPage);

    await dashboardPage.verifyPageheader();
    await dashboardPage.clickMyInfoMenu();
    await expect(authenticatedPage).toHaveURL(/viewPersonalDetails/);
    await dashboardPage.verifyMyInfoFormHeading();
  })


})