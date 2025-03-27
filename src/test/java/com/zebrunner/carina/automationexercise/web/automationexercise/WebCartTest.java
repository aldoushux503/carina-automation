package com.zebrunner.carina.automationexercise.web.automationexercise;

import com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise.CartPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise.ProductDetailPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise.ProductsPageBase;
import com.zebrunner.carina.automationexercise.core.AbstractWebTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WebCartTest extends AbstractWebTest {

    private ProductsPageBase productsPage;

    @Override
    protected void additionalSetUp() {
        productsPage = getVerifiedProductsPage();
    }

    @Test(dataProvider = "productsToAdd")
    public void verifyMultipleProductsCanBeAddedToCart(String testId, int firstProductIndex, int secondProductIndex, int expectedCount) {
        productsPage.addProductToCart(firstProductIndex);
        productsPage.clickContinueShopping();

        productsPage.addProductToCart(secondProductIndex);
        productsPage.clickContinueShopping();

        CartPageBase cartPage = productsPage.clickViewCart();
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page failed to load");

        int productCount = cartPage.getProductsInCart().size();
        Assert.assertEquals(productCount, expectedCount, "Cart should contain " + expectedCount + " products");

        Assert.assertFalse(cartPage.getProductDetails().isEmpty(), "Product details should be available");
    }

    @Test(dataProvider = "productQuantityData")
    public void verifyProductQuantityInCart(String testId, int productIndex, int quantity) {
        ProductDetailPageBase productDetailPage = productsPage.viewProductDetails(productIndex);
        Assert.assertTrue(productDetailPage.isProductDetailPageOpened(), "Product detail page failed to load");

        String productName = productDetailPage.getProductName();

        productDetailPage.setQuantity(quantity);
        Assert.assertEquals(productDetailPage.getQuantity(), quantity, "Product quantity was not set correctly");

        productDetailPage.clickAddToCart();
        CartPageBase cartPage = productDetailPage.clickViewCart();
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page failed to load");

        Assert.assertTrue(cartPage.isProductInCart(productName), "Product should be in cart");
        String actualQuantity = cartPage.getProductDetails().get(productName).get("quantity");
        Assert.assertEquals(actualQuantity, String.valueOf(quantity), "Product quantity should be " + quantity);
    }

    @Test(dataProvider = "productRemovalData")
    public void verifyProductRemovalFromCart(String testId, int productIndex) {
        productsPage.addProductToCart(productIndex);
        productsPage.clickContinueShopping();

        CartPageBase cartPage = productsPage.clickViewCart();
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page failed to load");
        Assert.assertFalse(cartPage.getProductsInCart().isEmpty(), "Cart should contain products");

        String productName = cartPage.getProductsInCart().get(0).getName();
        cartPage.removeProduct(productName);

        Assert.assertFalse(cartPage.isProductInCart(productName), "Product should be removed from cart");
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty");
    }

    @DataProvider(name = "productsToAdd")
    public Object[][] getProductsToAdd() {
        return new Object[][] {
                {"TC12-1: Add different products", 0, 1, 2},
                {"TC12-2: Add products from different categories", 1, 3, 2},
        };
    }

    @DataProvider(name = "productQuantityData")
    public Object[][] getProductQuantityData() {
        return new Object[][] {
                {"TC13-1: Standard quantity", 0, 4},
                {"TC13-2: Minimum quantity", 1, 1},
                {"TC13-3: Large quantity", 2, 10}
        };
    }

    @DataProvider(name = "productRemovalData")
    public Object[][] getProductRemovalData() {
        return new Object[][] {
                {"TC17-1: Remove popular product", 0},
                {"TC17-2: Remove medium-priced product", 1},
                {"TC17-3: Remove expensive product", 2}
        };
    }
}