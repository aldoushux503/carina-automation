package com.zebrunner.carina.automationexercise;

import com.zebrunner.carina.automationexercise.gui.pages.common.CartPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.HomePageBase;
import com.zebrunner.carina.core.IAbstractTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;

public class WebRecommendedItemsTest implements IAbstractTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private HomePageBase homePage;

    @BeforeMethod
    public void startDriver() {
        homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");
    }

    @Test
    public void testAddRecommendedItemToCart() {
        // Step 3: Scroll to bottom of page
        homePage.scrollToRecommendedItems();
        LOGGER.info("Scrolled to recommended items section");

        // Step 4: Verify 'RECOMMENDED ITEMS' are visible
        Assert.assertTrue(homePage.isRecommendedItemsSectionVisible(),
                "Recommended items section is not visible");
        LOGGER.info("Recommended items section is visible");

        // Get product name before adding to cart for later verification
        String productName = homePage.getRecommendedItemName(0);
        LOGGER.info("Selected recommended product name: {}", productName);

        // Step 5: Click on 'Add To Cart' on Recommended product
        LOGGER.info("Adding recommended product to cart");
        homePage.addRecommendedItemToCart(0);

        // Step 6: Click on 'View Cart' button
        LOGGER.info("Clicking View Cart button");
        CartPageBase cartPage = homePage.clickViewCartFromModal();

        // Step 7: Verify that product is displayed in cart page
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page is not opened");
        LOGGER.info("Cart page opened successfully");

        boolean isProductInCart = cartPage.isProductInCart(productName);
        LOGGER.info("Product in cart: {}", isProductInCart);
        Assert.assertTrue(isProductInCart,
                "Product '" + productName + "' is not displayed in cart page");
    }
}