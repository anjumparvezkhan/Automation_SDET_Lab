import {expect, Locator, Page} from '@playwright/test';

export class LoginPage {
  readonly page: Page
  readonly usernameInput: Locator;
  readonly passwordInput: Locator; 
  readonly loginButton: Locator;    
  readonly errorMessage: Locator;

  constructor(page: Page) {
    this.page = page;
    this.usernameInput = page.getByPlaceholder('Username');
    this.passwordInput = page.getByPlaceholder('Password');
    this.loginButton = page.getByRole('button', { name: 'Login' }) ;
    this.errorMessage = page.getByText('Invalid credentials');
  }

  async enterUsername(username: string) {
    await this.usernameInput.fill(username);
  } 
  
  async enterPassword(password: string) {
    await this.passwordInput.fill(password);
  }

  async clickLoginButton() {
    await this.loginButton.click();
  }

  async login(username: string, password: string) {
      await this.usernameInput.fill(username);
      await this.passwordInput.fill(password);
      await this.loginButton.click();
  }

  async getLoginError(): Promise<string> {
    return (await this.errorMessage.textContent())?.trim() ?? '';
  }
  async verifyErrorMessage() {
    await expect(this.errorMessage).toBeVisible({ timeout: 5000 });
  }

}