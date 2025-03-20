package com.zebrunner.carina.automationexercise.web;

import com.zebrunner.carina.automationexercise.gui.pages.common.CartPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.HomePageBase;
import com.zebrunner.carina.core.IAbstractTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WebRecommendedItemsTest implements IAbstractTest {

    private HomePageBase homePage;

    @BeforeMethod
    public void startDriver() {
        homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");
    }

    @Test
    public void testAddRecommendedItemToCart() {
        homePage.scrollToRecommendedItems();

        Assert.assertTrue(homePage.isRecommendedItemsSectionVisible(),
                "Recommended items section is not visible");

        String productName = homePage.getRecommendedItemName(0);

        homePage.addRecommendedItemToCart(0);

        CartPageBase cartPage = homePage.clickViewCartFromModal();
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page is not opened");


        Assert.assertTrue(cartPage.isProductInCart(productName),
                "Product '" + productName + "' is not displayed in cart page");
    }
}