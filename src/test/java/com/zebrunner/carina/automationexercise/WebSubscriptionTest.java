package com.zebrunner.carina.automationexercise;

import com.zebrunner.carina.automationexercise.gui.pages.common.CartPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.HomePageBase;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import com.zebrunner.carina.core.registrar.tag.Priority;
import com.zebrunner.carina.core.registrar.tag.TestPriority;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for subscription functionality
 */
public class WebSubscriptionTest implements IAbstractTest {

    private HomePageBase homePage;

    @BeforeMethod
    public void startDriver() {
        homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");
    }


    @Test(dataProvider = "subscriptionProvider")
    public void testSubscriptionInHomePage(String testId, String email) {
        homePage.scrollToSubscription();

        Assert.assertTrue(homePage.isSubscriptionSectionVisible(),
                "Subscription section is not visible in home page");

        homePage.subscribeWithEmail(email);

        Assert.assertTrue(homePage.isSubscriptionSuccessMessageVisible(),
                "Subscription success message is not visible");
    }

    @Test(dataProvider = "subscriptionProvider")
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

    @DataProvider()
    public Object[][] subscriptionProvider() {
        return new Object[][]{
                {"Subscription test - 1", "test_" + RandomStringUtils.randomAlphanumeric(6) + "@example.com"},
                {"Subscription test - 2", "test_" + RandomStringUtils.randomAlphanumeric(6) + "@example.com"}
        };
    }
}