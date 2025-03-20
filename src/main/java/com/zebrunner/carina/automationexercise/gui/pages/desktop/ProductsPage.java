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
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = ProductsPageBase.class)
public class ProductsPage extends ProductsPageBase {

    private static final String PRODUCT_NAME_XPATH = "//div[@class='productinfo text-center'][img[@src='/get_product_picture/%s']]/p";
    private static final String PRODUCT_PRICE_XPATH = "//div[@class='productinfo text-center'][img[@src='/get_product_picture/%s']]/h2";
    private static final String ADD_TO_CART_BUTTON_XPATH = "//a[@data-product-id='%s']";
    private static final String VIEW_PRODUCT_BUTTON_XPATH = "//a[contains(@href, '/product_details/%s')]";
    private static final String PRODUCT_ITEM_XPATH = "//div[@class='product-image-wrapper'][.//img[@src='/get_product_picture/%s']]";

    @FindBy(xpath = "//div[@class='features_items']")
    private ExtendedWebElement featuresItems;

    @FindBy(xpath = "//input[@id='search_product']")
    private ExtendedWebElement searchProductInput;

    @FindBy(xpath = "//button[@id='submit_search']")
    private ExtendedWebElement submitSearchButton;

    @FindBy(xpath = "//div[@class='features_items']//div[@class='product-image-wrapper']")
    private List<ExtendedWebElement> productItems;

    @FindBy(xpath = "//div[@class='features_items']//div[@class='product-image-wrapper']//img[contains(@src, '/get_product_picture/')]")
    private List<ExtendedWebElement> productImages;

    @FindBy(id = "cartModal")
    private ExtendedWebElement cartModal;

    @FindBy(xpath = "//button[contains(@class, 'close-modal')]")
    private ExtendedWebElement continueShoppingButton;

    @FindBy(xpath = "//a[contains(@href, 'view_cart')]")
    private ExtendedWebElement viewCartButton;

    private Map<Integer, Integer> indexToProductIdMap;

    public ProductsPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(submitSearchButton);
    }

    private void initializeProductMapping() {
        indexToProductIdMap = new HashMap<>();

        for (int i = 0; i < productImages.size(); i++) {
            ExtendedWebElement image = productImages.get(i);
            String imgSrc = image.getAttribute("src");
            Integer productId = extractProductIdFromSrc(imgSrc);

            if (productId != null) {
                indexToProductIdMap.put(i, productId);
            }
        }
    }


    private Integer extractProductIdFromSrc(String imgSrc) {
        Pattern pattern = Pattern.compile("/get_product_picture/(\\d+)");
        Matcher matcher = pattern.matcher(imgSrc);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return null;
    }


    private int getProductIdByIndex(int index) {
        if (indexToProductIdMap == null) {
            initializeProductMapping();
        }

        Integer productId = indexToProductIdMap.get(index);
        if (productId == null) {
            throw new IllegalArgumentException("Product ID not found for index: " + index);
        }

        return productId;
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
        return featuresItems.isElementPresent() &&
                !productItems.isEmpty() &&
                productItems.stream()
                        .allMatch(item -> item.getText().toLowerCase().contains(searchTerm.toLowerCase()));
    }

    @Override
    public List<ProductInfo> getProducts() {
        List<ProductInfo> products = new ArrayList<>();
        if (indexToProductIdMap == null) {
            initializeProductMapping();
        }

        for (Map.Entry<Integer, Integer> entry : indexToProductIdMap.entrySet()) {
            int productId = entry.getValue();
            String name = getProductNameElement(productId).getText();
            String price = getProductPriceElement(productId).getText();
            products.add(new ProductInfo(name, price));
        }
        return products;
    }

    @Override
    public void addProductToCart(int productIndex) {
        validateIndex(productIndex);
        int productId = getProductIdByIndex(productIndex);

        ExtendedWebElement productItem = getProductItemElement(productId);
        productItem.scrollTo();
        productItem.hover();

        getAddToCartButtonElement(productId).click();
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
        int productId = getProductIdByIndex(productIndex);

        getProductItemElement(productId).scrollTo();

        getViewProductButtonElement(productId).click();
        return initPage(getDriver(), ProductDetailPageBase.class);
    }

    private ExtendedWebElement getProductNameElement(int productId) {
        return findExtendedWebElement(By.xpath(String.format(PRODUCT_NAME_XPATH, productId)));
    }

    private ExtendedWebElement getProductPriceElement(int productId) {
        return findExtendedWebElement(By.xpath(String.format(PRODUCT_PRICE_XPATH, productId)));
    }

    private ExtendedWebElement getProductItemElement(int productId) {
        return findExtendedWebElement(By.xpath(String.format(PRODUCT_ITEM_XPATH, productId)));
    }

    private ExtendedWebElement getAddToCartButtonElement(int productId) {
        return findExtendedWebElement(By.xpath(String.format(ADD_TO_CART_BUTTON_XPATH, productId)));
    }

    private ExtendedWebElement getViewProductButtonElement(int productId) {
        return findExtendedWebElement(By.xpath(String.format(VIEW_PRODUCT_BUTTON_XPATH, productId)));
    }

    private boolean isAddToCartModalVisible() {
        return cartModal.isVisible();
    }

    private void validateIndex(int index) {
        if (indexToProductIdMap == null) {
            initializeProductMapping();
        }

        if (index < 0 || index >= indexToProductIdMap.size()) {
            throw new IllegalArgumentException("Product index out of bounds: " + index);
        }
    }
}