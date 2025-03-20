package com.zebrunner.carina.automationexercise.web;

import com.zebrunner.carina.automationexercise.gui.pages.common.ContactUsPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.HomePageBase;
import com.zebrunner.carina.core.IAbstractTest;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WebContactTest implements IAbstractTest {

    private HomePageBase homePage;

    @BeforeSuite
    public void startDriver() {
        homePage = initPage(getDriver(), HomePageBase.class);
    }

    @Test()
    public void testOpenHomePage() {
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");
    }

    @Test(dataProvider = "contactUsData", dependsOnMethods = "testOpenHomePage")
    public void testContactUs(String testId, String name, String email,
                              String subject, String message, String filePath) {
        ContactUsPageBase contactUsPage = homePage.getContactUsPage();

        Assert.assertTrue(contactUsPage.isPageOpened());
        contactUsPage.inputName(name);
        contactUsPage.inputEmail(email);
        contactUsPage.inputSubject(subject);
        contactUsPage.inputMessage(message);
        contactUsPage.attacheFile(filePath);
        contactUsPage.clickSubmitButton();
        Assert.assertTrue(contactUsPage.isSuccessMessageVisible());
    }

    @DataProvider(name = "contactUsData")
    public Object[][] contactUsData() {
        return new Object[][] {
                {"ContactUs test - 1" ,"Albert", "Albert@gmail.com", "Math", "Hello world", "src/test/resources/files/icon.png"}
        };
    }


}
