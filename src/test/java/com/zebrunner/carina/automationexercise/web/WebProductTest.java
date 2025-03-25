package com.zebrunner.carina.automationexercise.web;

import com.zebrunner.carina.automationexercise.gui.pages.common.HomePageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.ProductDetailPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.ProductsPageBase;
import com.zebrunner.carina.core.IAbstractTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for product search and review functionality
 */
public class WebProductTest implements IAbstractTest {

    private HomePageBase homePage;
    private ProductsPageBase productsPage;

    @BeforeMethod
    public void setUp() {
        homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page failed to load");

        productsPage = homePage.getProductsPage();
        Assert.assertTrue(productsPage.isPageOpened(), "Products page failed to load");
    }

    @Test(dataProvider = "searchTerms", description = "Verify product search functionality")
    public void testProductSearch(String testId, String searchTerm) {
        productsPage.searchProducts(searchTerm);
        productsPage.clickSubmitSearchButton();

        Assert.assertTrue(productsPage.areSearchResultsVisible(searchTerm),
                "Search results are not visible for term: " + searchTerm);
    }

    @Test(dataProvider = "reviewData", description = "Verify adding product reviews")
    public void testAddProductReview(String testId, int productIndex, String name, String email, String reviewText) {
        ProductDetailPageBase productDetailPage = productsPage.viewProductDetails(productIndex);
        Assert.assertTrue(productDetailPage.isProductDetailPageOpened(),
                "Product detail page failed to load");

        Assert.assertTrue(productDetailPage.isReviewSectionVisible(),
                "Review section is not visible on product page");

        productDetailPage.inputReviewName(name);
        productDetailPage.inputReviewEmail(email);
        productDetailPage.inputReviewText(reviewText);

        productDetailPage.submitReview();
        Assert.assertTrue(productDetailPage.isReviewSuccessMessageVisible(),
                "Review success message is not visible after submission");
    }

    @DataProvider(name = "searchTerms")
    public Object[][] searchTerms() {
        return new Object[][] {
                {"TC20-1: Search for jeans category", "jean"},
                {"TC20-2: Search for tops category", "top"}
        };
    }

    @DataProvider(name = "reviewData")
    public Object[][] reviewData() {
        return new Object[][] {
                {"TC21-1: Add review on product", 0,
                        "John Doe", "john" + RandomStringUtils.randomAlphanumeric(5) + "@example.com",
                        "This is a great product! I would definitely recommend it to others."},
                {"TC21-2: Add review on another product", 1,
                        "Jane Smith", "jane" + RandomStringUtils.randomAlphanumeric(5) + "@example.com",
                        "The quality of this product is excellent and the price is reasonable."}
        };
    }
}