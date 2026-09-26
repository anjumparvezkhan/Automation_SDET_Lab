import {test, expect} from '../../src/fixtures/test.js';


test.describe('Search User From List', () => {
    test('Search User', async({ dashboardPage, pimPage }) => {
        await dashboardPage.clickPimMenu();
        await pimPage.verifyEmpListHeader();
        await pimPage.enterEmpName('Amelia');
        await pimPage.clickSearch();
        await pimPage.verifyEmployeeNameDisplayed('Amelia')
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
        await pimPage.enterEmpName('Amelia');
        await pimPage.clickResetBtn();
        await pimPage.verifySearchFieldCleared();
    });
});