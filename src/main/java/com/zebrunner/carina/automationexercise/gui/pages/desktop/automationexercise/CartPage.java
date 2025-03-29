package com.zebrunner.carina.automationexercise.gui.pages.desktop.automationexercise;

import com.zebrunner.carina.automationexercise.gui.components.ProductInfo;
import com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise.CartPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = CartPageBase.class)
public class CartPage extends CartPageBase {

    private static final String CART_ITEMS_BASE = "//table[@id='cart_info_table']//tbody//tr[starts-with(@id, 'product-')]";

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

    @FindBy(xpath = CART_ITEMS_BASE)
    private List<ExtendedWebElement> cartItemsList;

    @FindBy(xpath = CART_ITEMS_BASE + "//td[@class='cart_description']/h4/a")
    private List<ExtendedWebElement> productNames;

    @FindBy(xpath = CART_ITEMS_BASE + "//td[@class='cart_price']/p")
    private List<ExtendedWebElement> productPrices;

    @FindBy(xpath = CART_ITEMS_BASE + "//td[@class='cart_quantity']/button")
    private List<ExtendedWebElement> productQuantities;

    @FindBy(xpath = CART_ITEMS_BASE + "//td[@class='cart_total']/p[@class='cart_total_price']")
    private List<ExtendedWebElement> productTotals;

    @FindBy(xpath = CART_ITEMS_BASE + "//td[@class='cart_delete']/a")
    private List<ExtendedWebElement> productDeleteButtons;

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
                subscriptionSuccessMessage.getText().contains("You have been successfully subscribed!");
    }

    @Override
    public List<ProductInfo> getProductsInCart() {
        List<ProductInfo> products = new ArrayList<>();

        for (int i = 0; i < cartItemsList.size(); i++) {
            String name = productNames.get(i).getText();
            String price = productPrices.get(i).getText();
            products.add(new ProductInfo(name, price));
        }

        return products;
    }

    @Override
    public Map<String, Map<String, String>> getProductDetails() {
        Map<String, Map<String, String>> productDetails = new HashMap<>();

        for (int i = 0; i < cartItemsList.size(); i++) {
            String name = productNames.get(i).getText();
            String price = productPrices.get(i).getText();
            String quantity = productQuantities.get(i).getText();
            String total = productTotals.get(i).getText();

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
        for (int i = 0; i < productNames.size(); i++) {
            if (productNames.get(i).getText().equals(productName)) {
                final int index = i;
                productDeleteButtons.get(i).click();
                waitUntil(d -> cartItemsList.get(index).isElementNotPresent(3), 3);
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
}