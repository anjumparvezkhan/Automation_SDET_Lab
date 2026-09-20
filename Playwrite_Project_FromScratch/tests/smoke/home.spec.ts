import {test, expect} from '@playwright/test';

test('Home page should have the correct title', async ({ page }) => {
    await page.goto('https://opensource-demo.orangehrmlive.com/web/index.php/auth/login',  { waitUntil: 'domcontentloaded' });
    const title = await page.title();
    expect(title).toBe('OrangeHRM');
});