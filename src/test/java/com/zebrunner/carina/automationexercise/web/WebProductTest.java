package com.zebrunner.carina.automationexercise.web;

import com.zebrunner.carina.automationexercise.gui.pages.common.HomePageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.ProductsPageBase;
import com.zebrunner.carina.core.IAbstractTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WebProductTest implements IAbstractTest {

    private HomePageBase homePage;

    @BeforeMethod
    public void startDriver() {
        homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");
    }

    @Test(dataProvider = "searchTerms")
    public void testProductSearch(String testId, String searchTerm) {
        ProductsPageBase productsPage = homePage.getProductsPage();
        Assert.assertTrue(productsPage.isPageOpened(), "Products page is not opened");

        productsPage.searchProducts(searchTerm);
        productsPage.clickSubmitSearchButton();

        Assert.assertTrue(productsPage.areSearchResultsVisible(searchTerm),
                "No search results are visible for term: " + searchTerm);
    }

    @DataProvider(name = "searchTerms")
    public Object[][] searchTerms() {
        return new Object[][] {
                {"Search test", "jean"},
                {"Search test", "top"}
        };
    }
}