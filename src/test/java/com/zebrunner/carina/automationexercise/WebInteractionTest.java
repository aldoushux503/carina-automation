package com.zebrunner.carina.automationexercise;

import com.zebrunner.carina.automationexercise.gui.pages.common.ContactUsPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.HomePageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.ProductsPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.desktop.HomePage;
import com.zebrunner.carina.core.IAbstractTest;
import org.apache.ibatis.io.Resources;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class WebInteractionTest implements IAbstractTest {

    private HomePageBase homePage;

    @BeforeSuite
    public void startDriver() {
        homePage = initPage(getDriver(), HomePageBase.class);
    }

    @Test()
    public void testOpenHomePage() {
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "true");
    }

//    @Test(dependsOnMethods = "testOpenHomePage")
//    public void testContactUs() {
//        ContactUsPageBase contactUsPage = homePage.getContactUsPage();
//
////        Assert.assertTrue(contactUsPage.isPageOpened());
//        contactUsPage.inputName("Albert");
//        contactUsPage.inputEmail("Albert@gmail.com");
//        contactUsPage.inputSubject("Math");
//        contactUsPage.inputMessage("Hello world");
//        contactUsPage.attacheFile("src/test/resources/files/icon.png");
//        contactUsPage.clickSubmitButton();
//    }


    @Test(dependsOnMethods = "testOpenHomePage")
    public void testProductSearch() {
        ProductsPageBase productsPage = homePage.getProductsPage();

        Assert.assertTrue(productsPage.isPageOpened(3));
        productsPage.searchProducts("Jean");
        productsPage.clickSubmitSearchButton();
    }
}
