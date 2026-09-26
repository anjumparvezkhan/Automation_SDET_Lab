import {expect,Locator,Page} from '@playwright/test'

export class PIMPage {
    readonly page : Page;
    readonly verifyEmpList : Locator;
    readonly nameInputField : Locator;
    readonly searchBtn : Locator;
    readonly noRecordFound : Locator;
    readonly resetbtn : Locator;
    readonly personalDetailsHeading : Locator;
    readonly firstName : Locator;
    readonly middleName : Locator;
    readonly lastName : Locator;
    readonly rowcell : Locator;
    readonly employeeId : Locator;

    constructor(page : Page){
        this.page = page;
        this.verifyEmpList = page.getByRole('heading' , {name:'PIM'});
        this.nameInputField = page.getByRole('textbox', { name: 'Type for hints...' }).first();
        this.searchBtn = page.getByRole('button', { name: 'Search' });
        this.noRecordFound = page.locator('.oxd-toast-content');
        this.resetbtn = page.getByRole('button', {name : 'Reset'});
        this.personalDetailsHeading = page.getByRole('heading', {name: 'Personal Details'})
        this.firstName  = page.getByPlaceholder('First Name')
        this.middleName  = page.getByPlaceholder('Middle Name')
        this.lastName  = page.getByPlaceholder('Last Name')
        this.rowcell = page.getByRole('cell').nth(1);
        this.employeeId = page.locator('input.oxd-input.oxd-input--active').nth(4)
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

    async clickUsertoOpenDetails(): Promise<void> {
        await this.rowcell.click();
    }

    async validateNavigationToDetailsPage(){
        await expect(this.page.url()).toContain('/viewPersonalDetails/empNumber/');
        await expect(this.personalDetailsHeading).toHaveText('Personal Details')
    }

    async verifyName(employeeName : string){
        await expect(this.firstName).toBeVisible();
        await expect(this.firstName).toBeEnabled();
        await expect(this.firstName).not.toHaveValue('');
        await expect(this.firstName).toHaveValue(employeeName);

        await expect(this.middleName).toBeVisible();
        await expect(this.middleName).toBeEnabled();
        await expect(this.middleName).toHaveValue('');
        
        await expect(this.lastName).toBeVisible();
        await expect(this.lastName).toBeEnabled();
        await expect(this.lastName).not.toHaveValue('');
    }

        async verifyEmployeeId(){
        await expect(this.employeeId).toBeVisible();
        await expect(this.employeeId).toBeEnabled();
        await expect(this.employeeId).not.toHaveValue('');
    }
}