package com.zebrunner.carina.automationexercise.gui.pages.common;


import com.zebrunner.carina.automationexercise.gui.components.ProductInfo;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

public abstract class CartPageBase extends AbstractPage {

    public CartPageBase(WebDriver driver) {
        super(driver);
    }


    public abstract void scrollToSubscription();


    public abstract boolean isSubscriptionSectionVisible();


    public abstract void subscribeWithEmail(String email);


    public abstract boolean isSubscriptionSuccessMessageVisible();


    public abstract List<ProductInfo> getProductsInCart();


    public abstract Map<String, Map<String, String>> getProductDetails();


    public abstract boolean isProductInCart(String productName);


    public abstract void removeProduct(String productName);


    public abstract boolean isCartEmpty();
}