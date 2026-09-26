import {expect,Locator,Page} from '@playwright/test'

export class PIMPage {
    readonly page : Page;
    readonly verifyEmpList : Locator;
    readonly nameInputField : Locator;
    readonly searchBtn : Locator;
    readonly noRecordFound : Locator;
    readonly resetbtn : Locator;

    constructor(page : Page){
        this.page = page;
        this.verifyEmpList = page.getByRole('heading' , {name:'PIM'});
        this.nameInputField = page.getByRole('textbox', { name: 'Type for hints...' }).first();
        this.searchBtn = page.getByRole('button', { name: 'Search' });
        this.noRecordFound = page.locator('.oxd-toast-content');
        this.resetbtn = page.getByRole('button', {name : 'Reset'});
    }

    async verifyEmpListHeader(){
        await expect (this.verifyEmpList).toBeVisible;
        await expect (this.nameInputField).toBeEnabled;
    }

    async enterEmpName(employeeName : string){
        await this.nameInputField.fill(employeeName);
    }

    async clickSearch(){
        await this.searchBtn.click();
    }

    async verifyEmployeeNameDisplayed(employeeName: string){
        await expect(this.page.getByText(employeeName)).toBeVisible;
        //await expect(this.page.getByText(employeeName)).toHaveText(employeeName);
    }

    async verifyNoRecordFound(){
        await expect(this.noRecordFound).toContainText('No Records Found');
        await expect(this.noRecordFound).toBeHidden({ timeout: 5000 });
    }

    async verifySearchFieldCleared(): Promise<void> {
        await expect(this.nameInputField).toBeEmpty();
    }

    async clickResetBtn(){
        await this.resetbtn.click();
    }
}