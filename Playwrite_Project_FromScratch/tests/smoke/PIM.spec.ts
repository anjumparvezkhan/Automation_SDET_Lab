import {test, expect} from '../../src/fixtures/test.js';


test.describe('Search User From List', () => {
    const employeeName : string = 'Emily';
    
    test('Search User', async({ dashboardPage, pimPage }) => {
        await dashboardPage.clickPimMenu();
        await pimPage.verifyEmpListHeader();
        await pimPage.enterEmpName(employeeName);
        await pimPage.clickSearch();
        await pimPage.verifyEmployeeNameDisplayed(employeeName) 
    });

        test('Invalid User Search', async({ dashboardPage, pimPage }) => {
        await dashboardPage.clickPimMenu();
        await pimPage.verifyEmpListHeader();
        await pimPage.enterEmpName('ZZZ_InvalidEmplayee');
        await pimPage.clickSearch();
        await pimPage.verifyNoRecordFound();
    });

        test('Reset Shearch Field', async({ dashboardPage, pimPage }) => {
        await dashboardPage.clickPimMenu();
        await pimPage.verifyEmpListHeader();
        await pimPage.enterEmpName(employeeName);
        await pimPage.clickResetBtn();
        await pimPage.verifySearchFieldCleared();
    });
        
        test('Search and Validate Employee Details', async({ dashboardPage, pimPage }) => {
        await dashboardPage.clickPimMenu();
        await pimPage.verifyEmpListHeader();
        await pimPage.enterEmpName(employeeName);
        await pimPage.clickSearch();
        await pimPage.verifyEmployeeNameDisplayed(employeeName)
        await pimPage.clickUsertoOpenDetails();
        await pimPage.validateNavigationToDetailsPage();
        await pimPage.verifyName(employeeName);
        await pimPage.verifyEmployeeId();
    });
});