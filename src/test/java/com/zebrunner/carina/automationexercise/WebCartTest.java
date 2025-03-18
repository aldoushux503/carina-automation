package com.zebrunner.carina.automationexercise;

import com.zebrunner.carina.automationexercise.gui.components.ProductInfo;
import com.zebrunner.carina.automationexercise.gui.pages.common.CartPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.HomePageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.ProductDetailPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.ProductsPageBase;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

/**
 * Tests for cart functionality from products page
 */
public class WebCartTest implements IAbstractTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private HomePageBase homePage;
    private ProductsPageBase productsPage;

    @BeforeMethod
    public void startDriver() {
        // Step 1: Launch browser
        // Step 2: Navigate to url 'http://automationexercise.com'
        homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();

        // Step 3: Verify that home page is visible successfully
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");
        LOGGER.info("Home page opened successfully");

        // Step 4: Click 'Products' button
        productsPage = homePage.getProductsPage();
        Assert.assertTrue(productsPage.isPageOpened(), "Products page is not opened");
        LOGGER.info("Products page opened successfully");
    }


    @Test
    @MethodOwner(owner = "qpsdemo")
    public void testAddProductsToCart() {
        // Get products for verification
        List<ProductInfo> products = productsPage.getProducts();
        Assert.assertTrue(products.size() >= 2, "Not enough products found on Products page");

        ProductInfo firstProduct = products.get(0);
        ProductInfo secondProduct = products.get(1);

        LOGGER.info("First product: {}", firstProduct);
        LOGGER.info("Second product: {}", secondProduct);

        // Step 5: Hover over first product and click 'Add to cart'
        LOGGER.info("Adding first product to cart");
        productsPage.addProductToCart(0);

        // Step 6: Click 'Continue Shopping' button
        LOGGER.info("Clicking Continue Shopping");
        productsPage.clickContinueShopping();

        // Step 7: Hover over second product and click 'Add to cart'
        LOGGER.info("Adding second product to cart");
        productsPage.addProductToCart(1);

        // Step 8: Click 'View Cart' button
        LOGGER.info("Clicking View Cart");
        CartPageBase cartPage = productsPage.clickViewCart();
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page is not opened");

        // Step 9: Verify both products are added to Cart
        List<ProductInfo> cartProducts = cartPage.getProductsInCart();
        LOGGER.info("Products in cart: {}", cartProducts.size());

        boolean firstProductFound = cartPage.isProductInCart(firstProduct.getName());
        boolean secondProductFound = cartPage.isProductInCart(secondProduct.getName());

        Assert.assertTrue(firstProductFound,
                "First product not found in cart: " + firstProduct.getName());
        Assert.assertTrue(secondProductFound,
                "Second product not found in cart: " + secondProduct.getName());

        // Step 10: Verify their prices, quantity and total price
        Map<String, Map<String, String>> productDetails = cartPage.getProductDetails();

        // Verify first product
        Map<String, String> firstProductDetails = productDetails.get(firstProduct.getName());
        Assert.assertNotNull(firstProductDetails, "Details for first product not found");

        String firstProductPrice = firstProductDetails.get("price");
        String firstProductQuantity = firstProductDetails.get("quantity");
        String firstProductTotal = firstProductDetails.get("total");

        LOGGER.info("First product - Price: {}, Quantity: {}, Total: {}",
                firstProductPrice, firstProductQuantity, firstProductTotal);

        Assert.assertEquals(firstProductPrice, firstProduct.getPrice(),
                "First product price doesn't match");
        Assert.assertEquals(firstProductQuantity, "1",
                "First product quantity should be 1");

        // Verify second product
        Map<String, String> secondProductDetails = productDetails.get(secondProduct.getName());
        Assert.assertNotNull(secondProductDetails, "Details for second product not found");

        String secondProductPrice = secondProductDetails.get("price");
        String secondProductQuantity = secondProductDetails.get("quantity");
        String secondProductTotal = secondProductDetails.get("total");

        LOGGER.info("Second product - Price: {}, Quantity: {}, Total: {}",
                secondProductPrice, secondProductQuantity, secondProductTotal);

        Assert.assertEquals(secondProductPrice, secondProduct.getPrice(),
                "Second product price doesn't match");
        Assert.assertEquals(secondProductQuantity, "1",
                "Second product quantity should be 1");
    }


    @Test
    @MethodOwner(owner = "qpsdemo")
    public void testProductQuantityInCart() {
        // Get a product for the test
        List<ProductInfo> products = productsPage.getProducts();
        Assert.assertFalse(products.isEmpty(), "No products found on Products page");

        ProductInfo targetProduct = products.get(0);
        LOGGER.info("Target product: {}", targetProduct);

        // Step 5: Click 'View Product' for the first product
        LOGGER.info("Viewing product details for first product");
        ProductDetailPageBase productDetailPage = productsPage.viewProductDetails(0);

        // Step 6: Verify product detail is opened
        Assert.assertTrue(productDetailPage.isProductDetailPageOpened(),
                "Product detail page is not opened");

        String detailPageProductName = productDetailPage.getProductName();
        LOGGER.info("Product detail page product name: {}", detailPageProductName);

        Assert.assertEquals(detailPageProductName, targetProduct.getName(),
                "Product name doesn't match between products page and detail page");

        // Step 7: Increase quantity to 4
        int targetQuantity = 4;
        LOGGER.info("Setting quantity to: {}", targetQuantity);
        productDetailPage.setQuantity(targetQuantity);

        // Verify the quantity was set correctly
        int actualQuantity = productDetailPage.getQuantity();
        LOGGER.info("Actual quantity set: {}", actualQuantity);
        Assert.assertEquals(actualQuantity, targetQuantity,
                "Quantity was not set correctly on product detail page");

        LOGGER.info("Adding product to cart");
        productDetailPage.clickAddToCart();

        LOGGER.info("Viewing cart");
        CartPageBase cartPage = productDetailPage.clickViewCart();
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page is not opened");

        Assert.assertTrue(cartPage.isProductInCart(targetProduct.getName()),
                "Product not found in cart: " + targetProduct.getName());

        Map<String, Map<String, String>> productDetails = cartPage.getProductDetails();
        Map<String, String> productInfo = productDetails.get(targetProduct.getName());

        Assert.assertNotNull(productInfo, "Product details not found in cart");

        String cartQuantity = productInfo.get("quantity");
        LOGGER.info("Cart quantity: {}, Expected: {}", cartQuantity, targetQuantity);

        Assert.assertEquals(cartQuantity, String.valueOf(targetQuantity),
                "Product quantity in cart doesn't match the set quantity");
    }


    @Test
    @MethodOwner(owner = "qpsdemo")
    public void testRemoveProductsFromCart() {
        // Get a product for the test
        List<ProductInfo> products = productsPage.getProducts();
        Assert.assertFalse(products.isEmpty(), "No products found on Products page");

        ProductInfo targetProduct = products.get(0);
        LOGGER.info("Target product: {}", targetProduct);

        // Step 5: Add products to cart
        LOGGER.info("Adding product to cart");
        productsPage.addProductToCart(0);

        // Step 6: Click 'Cart' button (View Cart)
        LOGGER.info("Viewing cart");
        CartPageBase cartPage = productsPage.clickViewCart();

        // Step 7: Verify that cart page is displayed
        Assert.assertTrue(cartPage.isPageOpened(), "Cart page is not opened");

        // Verify the product is in the cart first
        boolean productInCart = cartPage.isProductInCart(targetProduct.getName());
        LOGGER.info("Product in cart before removal: {}", productInCart);

        Assert.assertTrue(productInCart,
                "Product not found in cart before removal: " + targetProduct.getName());

        // Step 8: Click 'X' button corresponding to particular product
        LOGGER.info("Removing product from cart");
        cartPage.removeProduct(targetProduct.getName());

        // Step 9: Verify that product is removed from the cart
        boolean productStillInCart = cartPage.isProductInCart(targetProduct.getName());
        LOGGER.info("Product in cart after removal: {}", productStillInCart);

        Assert.assertFalse(productStillInCart,
                "Product still in cart after removal: " + targetProduct.getName());

        // Verify cart is empty
        boolean cartEmpty = cartPage.isCartEmpty();
        LOGGER.info("Cart is empty: {}", cartEmpty);

        Assert.assertTrue(cartEmpty, "Cart is not empty after removing product");
    }
}