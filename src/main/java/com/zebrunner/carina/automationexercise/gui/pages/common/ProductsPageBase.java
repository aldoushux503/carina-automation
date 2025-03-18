package com.zebrunner.carina.automationexercise.gui.pages.common;

import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;

public abstract class ProductsPageBase extends AbstractPage {

    public ProductsPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract void searchProducts(String product);

    public abstract void clickSubmitSearchButton();

    public abstract boolean areSearchResultsVisible(String searchTerm);
}
