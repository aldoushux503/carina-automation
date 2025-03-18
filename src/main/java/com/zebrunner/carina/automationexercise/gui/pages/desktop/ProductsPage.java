package com.zebrunner.carina.automationexercise.gui.pages.desktop;

import com.zebrunner.carina.automationexercise.gui.components.ProductInfo;
import com.zebrunner.carina.automationexercise.gui.pages.common.CartPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.ProductDetailPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.ProductsPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = ProductsPageBase.class)
public class ProductsPage extends ProductsPageBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = "//div[@class='features_items']")
    private List<ExtendedWebElement> featuresItemsBox;

    @FindBy(xpath = "//input[@id='search_product']")
    private ExtendedWebElement searchProductInput;

    @FindBy(xpath = "//button[@id='submit_search']")
    private ExtendedWebElement submitSearchButton;

    @FindBy(xpath = "//div[@class='features_items']//div[@class='product-image-wrapper']")
    private List<ExtendedWebElement> productItems;

    @FindBy(xpath = "//div[@class='features_items']//div[@class='productinfo']")
    private List<ExtendedWebElement> searchResults;


    public ProductsPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(submitSearchButton);
    }

    @Override
    public void searchProducts(String product) {
        searchProductInput.click();
        searchProductInput.type(product);
    }

    @Override
    public void clickSubmitSearchButton() {
        submitSearchButton.click();
    }

    @Override
    public boolean areSearchResultsVisible(String searchTerm) {
        if (featuresItemsBox.isEmpty()) {
            return false;
        }

        LOGGER.info("Found {} feature items", featuresItemsBox.size());
        for (ExtendedWebElement productName : featuresItemsBox) {
            String name = productName.getText().toLowerCase();
            if (!name.contains(searchTerm.toLowerCase())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public List<ProductInfo> getProducts() {
        List<ProductInfo> products = new ArrayList<>();

        for (ExtendedWebElement item : productItems) {
            String name = item.findExtendedWebElement(By.xpath(".//div[@class='productinfo text-center']/p")).getText();
            String price = item.findExtendedWebElement(By.xpath(".//div[@class='productinfo text-center']/h2")).getText();
            products.add(new ProductInfo(name, price));
        }

        return products;
    }

    @Override
    public void addProductToCart(int productIndex) {
        if (productIndex < 0 || productIndex >= productItems.size()) {
            throw new IllegalArgumentException("Product index out of bounds: " + productIndex);
        }

        ExtendedWebElement product = productItems.get(productIndex);
        LOGGER.info("Adding product at index {} to cart", productIndex);

        try {
            product.hover();

            String elementId = String.format("//a[contains(@class, 'add-to-cart')]//a[@data-product-id='%d']",  productIndex+1);
            // Get the Add to cart button
            ExtendedWebElement addToCartButton = product.findExtendedWebElement(
                    By.xpath(".//div[@class='product-overlay']"+elementId)
            );

            LOGGER.info("Found add to cart button: {}", addToCartButton.isElementPresent());
            addToCartButton.click();

            // Wait for modal to appear
            waitUntil(d -> isAddToCartModalVisible(), 10);
            LOGGER.info("Modal visible: {}", isAddToCartModalVisible());
        } catch (Exception e) {
            LOGGER.error("Error adding product to cart: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void clickContinueShopping() {
        try {
            LOGGER.info("Clicking continue shopping button");
            ExtendedWebElement continueButton = findExtendedWebElement(
                    By.xpath("//button[contains(@class, 'close-modal')]")
            );
            continueButton.click();

            // Wait for modal to disappear
            waitUntil(d -> !isAddToCartModalVisible(), 10);
        } catch (Exception e) {
            LOGGER.error("Error clicking continue shopping: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public CartPageBase clickViewCart() {
        try {
            LOGGER.info("Clicking view cart button");
            ExtendedWebElement viewCartButton = findExtendedWebElement(
                    By.xpath("//div[@class='modal-body']//a[contains(text(), 'View Cart')]")
            );
            viewCartButton.click();
            return initPage(getDriver(), CartPageBase.class);
        } catch (Exception e) {
            LOGGER.error("Error clicking view cart: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ProductDetailPageBase viewProductDetails(int productIndex) {
        if (productIndex < 0 || productIndex >= productItems.size()) {
            throw new IllegalArgumentException("Product index out of bounds: " + productIndex);
        }

        ExtendedWebElement product = productItems.get(productIndex);
        try {
            LOGGER.info("Viewing product details at index {}", productIndex);
            ExtendedWebElement viewProductButton = product.findExtendedWebElement(
                    By.xpath(".//a[contains(text(), 'View Product')]")
            );
            viewProductButton.click();

            return initPage(getDriver(), ProductDetailPageBase.class);
        } catch (Exception e) {
            LOGGER.error("Error viewing product details: {}", e.getMessage(), e);
            throw e;
        }
    }

    private boolean isAddToCartModalVisible() {
        return findExtendedWebElement(By.id("cartModal")).isVisible();
    }
}