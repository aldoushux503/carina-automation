package com.zebrunner.carina.automationexercise;

import com.zebrunner.carina.automationexercise.gui.pages.common.CategoryPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.HomePageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.ProductsPageBase;
import com.zebrunner.carina.core.IAbstractTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;

public class WebCategoryProductsTest implements IAbstractTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private HomePageBase homePage;

    @BeforeMethod
    public void startDriver() {
        homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");
    }

    @Test
    public void testViewCategoryProducts() {
        Assert.assertTrue(homePage.areCategoriesVisible(),
                "Categories are not visible on left side bar");
        LOGGER.info("Categories are visible on left side bar");

        LOGGER.info("Clicking on Women category");
        homePage.clickOnCategory("WOMEN");

        LOGGER.info("Clicking on Dress subcategory under Women");
        ProductsPageBase categoryPage = homePage.clickOnSubcategory("WOMEN", "Dress");

        Assert.assertTrue(categoryPage.isPageOpened(), "Category page is not opened");
        Boolean isResultVisible = categoryPage.areSearchResultsVisible("Dress");
        LOGGER.info("Category page title: {}", isResultVisible);
        Assert.assertTrue(isResultVisible,
                "Category page title does not match expected");
    }
}