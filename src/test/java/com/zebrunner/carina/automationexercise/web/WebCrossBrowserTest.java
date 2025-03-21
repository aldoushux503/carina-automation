package com.zebrunner.carina.automationexercise.web;

import com.zebrunner.carina.automationexercise.gui.pages.common.HomePageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.ProductsPageBase;

import com.zebrunner.carina.automationexercise.gui.pages.desktop.HomePage;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.invoke.MethodHandles;

/**
 * This test class demonstrates how to run the same tests on multiple browsers
 * using TestNG's data provider with our BrowserFactory
 */
public class WebCrossBrowserTest implements IAbstractTest {

    private final String browser;
    private final String testId;

    private HomePageBase homePage;

    @Factory(dataProvider = "browsersData")
    public WebCrossBrowserTest(String testId, String browser) {
        this.testId = testId;
        this.browser = browser;
        R.CONFIG.put("capabilities.browserName", browser);
    }

    @DataProvider(name = "browsersData")
    public static Object[][] getBrowsers() {
        return new Object[][] {
                {"TC-B01: Chrome Browser Testing", "chrome"},
                {"TC-B02: Firefox Browser Testing", "firefox"},

        };
    }

    @BeforeMethod
    public void setUp() {
        homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(),
                String.format("[%s] Home page is not opened for browser: %s", testId, browser));
    }

    @Test
    @MethodOwner(owner = "AutomationExercise")
    public void testSubscriptionOnBrowser() {
        homePage.scrollToSubscription();
        Assert.assertTrue(homePage.isSubscriptionSectionVisible(),
                String.format("[%s] Subscription section not visible on browser: %s", testId, browser));

        String email = "test" + System.currentTimeMillis() + "@example.com";
        homePage.subscribeWithEmail(email);

        Assert.assertTrue(homePage.isSubscriptionSuccessMessageVisible(),
                String.format("[%s] Subscription success message not visible on browser: %s", testId, browser));
    }

    @Test
    @MethodOwner(owner = "AutomationExercise")
    public void testRecommendedItemsOnBrowser() {
        homePage.scrollToRecommendedItems();
        Assert.assertTrue(homePage.isRecommendedItemsSectionVisible(),
                String.format("[%s] Recommended items section not visible on browser: %s", testId, browser));

        String productName = homePage.getRecommendedItemName(0);
        Assert.assertFalse(productName.isEmpty(),
                String.format("[%s] Recommended product name should not be empty on browser: %s", testId, browser));

        homePage.addRecommendedItemToCart(0);
    }
}