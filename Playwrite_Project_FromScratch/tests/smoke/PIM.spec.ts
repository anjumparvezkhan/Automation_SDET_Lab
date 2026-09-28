import {test, expect} from '../../src/fixtures/test.js';
import {employeeData} from '../../test-data/employees.js'


test.describe('Search User From List', () => {
    //const employeeName : string = 'Emily';
    
    test('Search User', async({ dashboardPage, pimPage }) => {
        await dashboardPage.clickPimMenu();
        await pimPage.verifyEmpListHeader();
        await pimPage.enterEmpName(employeeData.validEmployee.name);
        await pimPage.clickSearch();
        await pimPage.verifyEmployeeNameDisplayed(employeeData.validEmployee.name) 
    });

        test('Invalid User Search', async({ dashboardPage, pimPage }) => {
        await dashboardPage.clickPimMenu();
        await pimPage.verifyEmpListHeader();
        await pimPage.enterEmpName(employeeData.invalidEmployee.name);
        await pimPage.clickSearch();
        await pimPage.verifyNoRecordFound();
    });

        test('Reset Shearch Field', async({ dashboardPage, pimPage }) => {
        await dashboardPage.clickPimMenu();
        await pimPage.verifyEmpListHeader();
        await pimPage.enterEmpName(employeeData.validEmployee.name);
        await pimPage.clickResetBtn();
        await pimPage.verifySearchFieldCleared();
    });
        
        test('Search and Validate Employee Details', async({ dashboardPage, pimPage }) => {
        await dashboardPage.clickPimMenu();
        await pimPage.verifyEmpListHeader();
        await pimPage.enterEmpName(employeeData.validEmployee.name);
        await pimPage.clickSearch();
        await pimPage.verifyEmployeeNameDisplayed(employeeData.validEmployee.name)
        await pimPage.clickUsertoOpenDetails();
        await pimPage.validateNavigationToDetailsPage();
        await pimPage.verifyName(employeeData.validEmployee.name);
        await pimPage.verifyEmployeeId();
    });

        test('Update Employee First Name and validate Details', async({ dashboardPage, pimPage, page }) => {
        await dashboardPage.clickPimMenu();
        await pimPage.verifyEmpListHeader();
        await pimPage.enterEmpName(employeeData.validEmployee.name);
        await pimPage.clickSearch();
        await pimPage.verifyEmployeeNameDisplayed(employeeData.validEmployee.name)
        await pimPage.clickUsertoOpenDetails();
        await pimPage.updateFirstName(employeeData.validEmployee.name+"_Updated"); 
        await pimPage.clicksavePersonalDetails();
        await pimPage.verifyRecordSuccesfullyUpdate();
        await pimPage.verifyUpdatedFirstName(employeeData.validEmployee.name+"_Updated")
    });
});