package com.zebrunner.carina.automationexercise.web;

import com.zebrunner.carina.automationexercise.gui.pages.common.CartPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.HomePageBase;
import com.zebrunner.carina.core.IAbstractTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for newsletter subscription functionality
 */
public class WebSubscriptionTest implements IAbstractTest {

    private HomePageBase homePage;

    @BeforeMethod
    public void setUp() {
        homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page failed to load");
    }

    @Test(dataProvider = "validEmailProvider")
    public void testSubscriptionInHomePage(String testId, String email) {
        homePage.scrollToSubscription();
        Assert.assertTrue(homePage.isSubscriptionSectionVisible(),
                "Subscription section is not visible in home page");

        homePage.subscribeWithEmail(email);

        Assert.assertTrue(homePage.isSubscriptionSuccessMessageVisible(),
                "Subscription success message is not visible after submitting valid email");
    }

    @Test(dataProvider = "validEmailProvider")
    public void testSubscriptionInCartPage(String testId, String email) {
        CartPageBase cartPage = homePage.getCartPage();
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page failed to load");

        cartPage.scrollToSubscription();
        Assert.assertTrue(cartPage.isSubscriptionSectionVisible(),
                "Subscription section is not visible in cart page");

        cartPage.subscribeWithEmail(email);

        Assert.assertTrue(cartPage.isSubscriptionSuccessMessageVisible(),
                "Subscription success message is not visible after submitting valid email in cart page");
    }

    @Test(dataProvider = "invalidEmailProvider")
    public void testInvalidSubscriptionInHomePage(String testId, String invalidEmail) {
        homePage.scrollToSubscription();

        homePage.subscribeWithEmail(invalidEmail);

        Assert.assertFalse(homePage.isSubscriptionSuccessMessageVisible(),
                "Subscription success message should not be visible with invalid email format");
    }

    @Test(dataProvider = "invalidEmailProvider")
    public void testInvalidSubscriptionInCartPage(String testId, String invalidEmail) {
        CartPageBase cartPage = homePage.getCartPage();
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page failed to load");

        cartPage.scrollToSubscription();

        cartPage.subscribeWithEmail(invalidEmail);

        Assert.assertFalse(cartPage.isSubscriptionSuccessMessageVisible(),
                "Subscription success message should not be visible with invalid email format in cart page");
    }

    @DataProvider(name = "validEmailProvider")
    public Object[][] validEmailProvider() {
        return new Object[][]{
                {"TC30-1: Valid subscription with random email",
                        "test_" + RandomStringUtils.randomAlphanumeric(6) + "@example.com"},
                {"TC30-2: Valid subscription with another random email",
                        "user_" + RandomStringUtils.randomAlphanumeric(6) + "@example.com"}
        };
    }

    @DataProvider(name = "invalidEmailProvider")
    public Object[][] invalidEmailProvider() {
        return new Object[][]{
                {"TC31-1: Invalid subscription with missing @ symbol", "invalidemail"},
                {"TC31-2: Invalid subscription with missing domain", "test@"}
        };
    }
}