package com.zebrunner.carina.automationexercise.web.automationexercise;

import com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise.ProductDetailPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise.ProductsPageBase;
import com.zebrunner.carina.automationexercise.core.AbstractWebTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for product search and review functionality
 */
public class ProductTest extends AbstractWebTest {

    private ProductsPageBase productsPage;

    @Override
    protected void additionalSetUp() {
        productsPage = getVerifiedProductsPage();
    }

    @Test(dataProvider = "searchTerms")
    public void testProductSearch(String testId, String searchTerm) {
        productsPage.searchProducts(searchTerm);
        productsPage.clickSubmitSearchButton();

        Assert.assertTrue(productsPage.areSearchResultsVisible(searchTerm),
                "Search results are not visible for term: " + searchTerm);
    }

    @Test(dataProvider = "reviewData")
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