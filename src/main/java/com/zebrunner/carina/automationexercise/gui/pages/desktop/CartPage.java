package com.zebrunner.carina.automationexercise.gui.pages.desktop;

import com.zebrunner.carina.automationexercise.gui.components.ProductInfo;
import com.zebrunner.carina.automationexercise.gui.pages.common.CartPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = CartPageBase.class)
public class CartPage extends CartPageBase {

    private static final String CART_ITEM_ROW_XPATH = "//table[@id='cart_info_table']//tbody//tr[@id='product-%d']";
    private static final String PRODUCT_NAME_XPATH = CART_ITEM_ROW_XPATH + "//td[@class='cart_description']/h4/a";
    private static final String PRODUCT_PRICE_XPATH = CART_ITEM_ROW_XPATH + "//td[@class='cart_price']/p";
    private static final String PRODUCT_QUANTITY_XPATH = CART_ITEM_ROW_XPATH + "//td[@class='cart_quantity']/button";
    private static final String PRODUCT_TOTAL_XPATH = CART_ITEM_ROW_XPATH + "//td[@class='cart_total']/p[@class='cart_total_price']";
    private static final String PRODUCT_DELETE_XPATH = CART_ITEM_ROW_XPATH + "//td[@class='cart_delete']/a[@data-product-id='%d']";

    @FindBy(id = "cart_info")
    private ExtendedWebElement cartInfoTable;

    @FindBy(xpath = "//h2[text()='Subscription']")
    private ExtendedWebElement subscriptionTitleText;

    @FindBy(id = "susbscribe_email")
    private ExtendedWebElement subscribeEmailField;

    @FindBy(id = "subscribe")
    private ExtendedWebElement subscribeButton;

    @FindBy(xpath = "//div[@id='success-subscribe']//div[@class='alert-success alert']")
    private ExtendedWebElement subscriptionSuccessMessage;

    @FindBy(xpath = "//table[@id='cart_info_table']//tbody//tr[starts-with(@id, 'product-')]")
    private List<ExtendedWebElement> cartItemsList;

    @FindBy(id = "empty_cart")
    private ExtendedWebElement emptyCartMessage;

    public CartPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(cartInfoTable);
        setPageURL("/view_cart");
    }

    @Override
    public void scrollToSubscription() {
        subscriptionTitleText.scrollTo();
    }

    @Override
    public boolean isSubscriptionSectionVisible() {
        return subscriptionTitleText.isElementPresent() &&
                subscriptionTitleText.getText().equals("SUBSCRIPTION");
    }

    @Override
    public void subscribeWithEmail(String email) {
        subscribeEmailField.type(email);
        subscribeButton.click();
    }

    @Override
    public boolean isSubscriptionSuccessMessageVisible() {
        return subscriptionSuccessMessage.isElementPresent() &&
                subscriptionSuccessMessage.getText().contains("You have been successfully subscribed!") &&
                findExtendedWebElement(By.id("success-subscribe")).getAttribute("class").contains("form-group") &&
                !findExtendedWebElement(By.id("success-subscribe")).getAttribute("class").contains("hide");
    }

    @Override
    public List<ProductInfo> getProductsInCart() {
        List<ProductInfo> products = new ArrayList<>();

        for (ExtendedWebElement item : cartItemsList) {
            String id = item.getAttribute("id").replace("product-", "");
            int productId = Integer.parseInt(id);

            String name = getProductNameElement(productId).getText();
            String price = getProductPriceElement(productId).getText();
            products.add(new ProductInfo(name, price));
        }

        return products;
    }

    @Override
    public Map<String, Map<String, String>> getProductDetails() {
        Map<String, Map<String, String>> productDetails = new HashMap<>();

        for (ExtendedWebElement item : cartItemsList) {
            String id = item.getAttribute("id").replace("product-", "");
            int productId = Integer.parseInt(id);

            String name = getProductNameElement(productId).getText();
            String price = getProductPriceElement(productId).getText();
            String quantity = getProductQuantityElement(productId).getText();
            String total = getProductTotalElement(productId).getText();

            Map<String, String> details = new HashMap<>();
            details.put("price", price);
            details.put("quantity", quantity);
            details.put("total", total);

            productDetails.put(name, details);
        }

        return productDetails;
    }

    @Override
    public boolean isProductInCart(String productName) {
        return getProductsInCart().stream()
                .anyMatch(product -> product.getName().equals(productName));
    }

    @Override
    public void removeProduct(String productName) {
        for (ExtendedWebElement item : cartItemsList) {
            String id = item.getAttribute("id").replace("product-", "");
            int productId = Integer.parseInt(id);

            if (getProductNameElement(productId).getText().equals(productName)) {
                getProductDeleteElement(productId).click();
                waitUntil(d -> item.isElementNotPresent(3), 3);
                return;
            }
        }
    }

    @Override
    public boolean isCartEmpty() {
        return emptyCartMessage.isElementPresent() &&
                emptyCartMessage.isVisible() &&
                emptyCartMessage.getAttribute("style").contains("display: block");
    }

    // Private helper methods
    private ExtendedWebElement getProductNameElement(int index) {
        return findExtendedWebElement(By.xpath(String.format(PRODUCT_NAME_XPATH, index)));
    }

    private ExtendedWebElement getProductPriceElement(int index) {
        return findExtendedWebElement(By.xpath(String.format(PRODUCT_PRICE_XPATH, index)));
    }

    private ExtendedWebElement getProductQuantityElement(int index) {
        return findExtendedWebElement(By.xpath(String.format(PRODUCT_QUANTITY_XPATH, index)));
    }

    private ExtendedWebElement getProductTotalElement(int index) {
        return findExtendedWebElement(By.xpath(String.format(PRODUCT_TOTAL_XPATH, index)));
    }

    private ExtendedWebElement getProductDeleteElement(int index) {
        return findExtendedWebElement(By.xpath(String.format(PRODUCT_DELETE_XPATH, index, index)));
    }
}