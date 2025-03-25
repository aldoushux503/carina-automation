package com.zebrunner.carina.automationexercise.web;

import com.zebrunner.carina.automationexercise.gui.pages.common.CartPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.HomePageBase;
import com.zebrunner.carina.core.IAbstractTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for recommended items functionality
 */
public class WebRecommendedItemsTest implements IAbstractTest {

    private HomePageBase homePage;

    @BeforeMethod
    public void setUp() {
        homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page failed to load");
    }

    @Test
    public void testAddRecommendedItemToCart() {
        homePage.scrollToRecommendedItems();

        Assert.assertTrue(homePage.isRecommendedItemsSectionVisible(),
                "Recommended items section should be visible on home page");

        String productName = homePage.getRecommendedItemName(0);
        Assert.assertFalse(productName.isEmpty(), "Product name should not be empty");

        homePage.addRecommendedItemToCart(0);

        CartPageBase cartPage = homePage.clickViewCartFromModal();
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page failed to load");

        Assert.assertTrue(cartPage.isProductInCart(productName),
                "Product '" + productName + "' should be present in cart after adding from recommended items");
    }
}