package com.zebrunner.carina.automationexercise.gui.pages.desktop;

import com.zebrunner.carina.automationexercise.gui.components.ProductInfo;
import com.zebrunner.carina.automationexercise.gui.pages.common.CartPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.*;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = CartPageBase.class)
public class CartPage extends CartPageBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = "//div[@class='table-responsive cart_info']")
    private ExtendedWebElement cartInfo;

    @FindBy(xpath = "//h2[contains(text(),'Subscription')]")
    private ExtendedWebElement subscriptionTitle;

    @FindBy(id = "susbscribe_email")
    private ExtendedWebElement subscribeEmailField;

    @FindBy(id = "subscribe")
    private ExtendedWebElement subscribeButton;

    @FindBy(xpath = "//div[@class='alert-success alert']")
    private ExtendedWebElement subscriptionSuccessMessage;

    @FindBy(xpath = "//tbody//tr")
    private List<ExtendedWebElement> cartItems;

    @FindBy(id = "empty_cart")
    private ExtendedWebElement emptyCartMessage;

    public CartPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(cartInfo);
        setPageURL("/view_cart");
    }

    @Override
    public void scrollToSubscription() {
        subscriptionTitle.scrollTo();
    }

    @Override
    public boolean isSubscriptionSectionVisible() {
        return subscriptionTitle.isElementPresent() &&
                subscriptionTitle.getText().equals("SUBSCRIPTION");
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

        for (ExtendedWebElement item : cartItems) {
            String name = item.findExtendedWebElement(By.xpath(".//h4/a")).getText();
            String price = item.findExtendedWebElement(By.xpath(".//td[@class='cart_price']/p")).getText();
            products.add(new ProductInfo(name, price));
        }

        return products;
    }

    @Override
    public Map<String, Map<String, String>> getProductDetails() {
        Map<String, Map<String, String>> productDetails = new HashMap<>();

        for (ExtendedWebElement item : cartItems) {
            String name = item.findExtendedWebElement(By.xpath(".//h4/a")).getText();
            String price = item.findExtendedWebElement(By.xpath(".//td[@class='cart_price']/p")).getText();
            String quantity = item.findExtendedWebElement(By.xpath(".//td[@class='cart_quantity']/button")).getText();
            String total = item.findExtendedWebElement(By.xpath(".//td[@class='cart_total']/p")).getText();

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
        for (ProductInfo product : getProductsInCart()) {
            if (product.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeProduct(String productName) {
        for (ExtendedWebElement item : cartItems) {
            ExtendedWebElement itemName = item.findExtendedWebElement(By.xpath(".//h4/a"));
            if (itemName.getText().equals(productName)) {
                ExtendedWebElement deleteButton = item.findExtendedWebElement(By.xpath(".//td[@class='cart_delete']/a"));
                deleteButton.click();
                return;
            }
        }
        LOGGER.warn("Product '{}' not found in cart", productName);
    }

    @Override
    public boolean isCartEmpty() {
        return emptyCartMessage.isElementPresent();
    }
}