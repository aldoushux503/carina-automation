package com.zebrunner.carina.automationexercise.gui.pages.desktop.automationexercise;

import com.zebrunner.carina.automationexercise.gui.components.ProductInfo;
import com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise.CartPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise.ProductDetailPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise.ProductsPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = ProductsPageBase.class)
public class ProductsPage extends ProductsPageBase {

    private static final String PRODUCT_ITEMS_BASE = "//div[@class='features_items']//div[@class='product-image-wrapper']";

    @FindBy(xpath = "//div[@class='features_items']")
    private ExtendedWebElement featuresItems;

    @FindBy(xpath = "//input[@id='search_product']")
    private ExtendedWebElement searchProductInput;

    @FindBy(xpath = "//button[@id='submit_search']")
    private ExtendedWebElement submitSearchButton;

    @FindBy(xpath = PRODUCT_ITEMS_BASE)
    private List<ExtendedWebElement> productItems;

    @FindBy(xpath = PRODUCT_ITEMS_BASE + "//div[@class='productinfo text-center']/p")
    private List<ExtendedWebElement> productNames;

    @FindBy(xpath = PRODUCT_ITEMS_BASE + "//div[@class='productinfo text-center']/h2")
    private List<ExtendedWebElement> productPrices;

    @FindBy(xpath = PRODUCT_ITEMS_BASE + "//a[contains(@class, 'add-to-cart')]")
    private List<ExtendedWebElement> addToCartButtons;

    @FindBy(xpath = PRODUCT_ITEMS_BASE + "//a[contains(@href, '/product_details/')]")
    private List<ExtendedWebElement> viewProductButtons;

    @FindBy(id = "cartModal")
    private ExtendedWebElement cartModal;

    @FindBy(xpath = "//button[contains(@class, 'close-modal')]")
    private ExtendedWebElement continueShoppingButton;

    @FindBy(xpath = "//a[contains(@href, 'view_cart')]")
    private ExtendedWebElement viewCartButton;

    public ProductsPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(submitSearchButton);
    }

    @Override
    public void searchProducts(String product) {
        searchProductInput.type(product);
    }

    @Override
    public void clickSubmitSearchButton() {
        submitSearchButton.click();
    }

    @Override
    public boolean areSearchResultsVisible(String searchTerm) {
        if (!featuresItems.isElementPresent() || productItems.isEmpty()) {
            return false;
        }

        for (int i = 0; i < productNames.size(); i++) {
            if (productNames.get(i).getText().contains(searchTerm)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ProductInfo> getProducts() {
        List<ProductInfo> products = new ArrayList<>();

        for (int i = 0; i < productItems.size(); i++) {
            String name = productNames.get(i).getText();
            String price = productPrices.get(i).getText();
            products.add(new ProductInfo(name, price));
        }
        return products;
    }

    @Override
    public void addProductToCart(int productIndex) {
        validateIndex(productIndex);

        productItems.get(productIndex).scrollTo();
        productItems.get(productIndex).hover();

        addToCartButtons.get(productIndex).click();
        waitUntil(d -> isAddToCartModalVisible(), 2);
    }

    @Override
    public void clickContinueShopping() {
        continueShoppingButton.click();
        waitUntil(d -> !isAddToCartModalVisible(), 2);
    }

    @Override
    public CartPageBase clickViewCart() {
        viewCartButton.click();
        return initPage(getDriver(), CartPageBase.class);
    }

    @Override
    public ProductDetailPageBase viewProductDetails(int productIndex) {
        validateIndex(productIndex);

        productItems.get(productIndex).scrollTo();

        viewProductButtons.get(productIndex).click();
        return initPage(getDriver(), ProductDetailPageBase.class);
    }

    private boolean isAddToCartModalVisible() {
        return cartModal.isVisible();
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= productItems.size()) {
            throw new IllegalArgumentException("Product index out of bounds: " + index);
        }
    }
}