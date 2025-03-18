package com.zebrunner.carina.automationexercise.gui.pages.common;

import com.zebrunner.carina.automationexercise.gui.components.ProductInfo;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;

import java.util.List;

public abstract class ProductsPageBase extends AbstractPage {

    public ProductsPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract void searchProducts(String product);

    public abstract void clickSubmitSearchButton();

    public abstract boolean areSearchResultsVisible(String searchTerm);


    public abstract List<ProductInfo> getProducts();

    public abstract void addProductToCart(int productIndex);


    public abstract void clickContinueShopping();


    public abstract CartPageBase clickViewCart();


    public abstract ProductDetailPageBase viewProductDetails(int productIndex);
}
