package com.zebrunner.carina.automationexercise.web;

import com.zebrunner.carina.automationexercise.gui.pages.common.CartPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.HomePageBase;
import com.zebrunner.carina.core.IAbstractTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WebSubscriptionTest implements IAbstractTest {

    private HomePageBase homePage;

    @BeforeMethod
    public void startDriver() {
        homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");
    }

    @Test(dataProvider = "validEmailProvider")
    public void testSubscriptionInHomePage(String testId, String email) {
        homePage.scrollToSubscription();
        Assert.assertTrue(homePage.isSubscriptionSectionVisible(),
                "Subscription section is not visible in home page");

        homePage.subscribeWithEmail(email);

        Assert.assertTrue(homePage.isSubscriptionSuccessMessageVisible(),
                "Subscription success message is not visible");
    }

    @Test(dataProvider = "validEmailProvider")
    public void testSubscriptionInCartPage(String testId, String email) {
        CartPageBase cartPage = homePage.getCartPage();
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page is not opened");

        cartPage.scrollToSubscription();
        Assert.assertTrue(cartPage.isSubscriptionSectionVisible(),
                "Subscription section is not visible in cart page");

        cartPage.subscribeWithEmail(email);

        Assert.assertTrue(cartPage.isSubscriptionSuccessMessageVisible(),
                "Subscription success message is not visible");
    }

    @Test(dataProvider = "invalidEmailProvider")
    public void testInvalidSubscriptionInHomePage(String testId, String invalidEmail) {
        homePage.scrollToSubscription();

        homePage.subscribeWithEmail(invalidEmail);

        Assert.assertFalse(homePage.isSubscriptionSuccessMessageVisible(),
                "Subscription success message should not be visible");
    }

    @Test(dataProvider = "invalidEmailProvider")
    public void testInvalidSubscriptionInCartPage(String testId, String invalidEmail) {
        CartPageBase cartPage = homePage.getCartPage();
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page is not opened");

        cartPage.scrollToSubscription();

        cartPage.subscribeWithEmail(invalidEmail);

        Assert.assertFalse(cartPage.isSubscriptionSuccessMessageVisible(),
                "Subscription success message should not be visible");
    }

    @DataProvider(name = "validEmailProvider")
    public Object[][] validEmailProvider() {
        return new Object[][]{
                {"Valid subscription", "test_" + RandomStringUtils.randomAlphanumeric(6) + "@example.com"},
        };
    }

    @DataProvider(name = "invalidEmailProvider")
    public Object[][] invalidEmailProvider() {
        return new Object[][]{
                {"Invalid subscription", "invalidemail"},
        };
    }
}