package com.zebrunner.carina.automationexercise.gui.pages.desktop;

import com.zebrunner.carina.automationexercise.gui.pages.common.ProductsPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = ProductsPageBase.class)
public class ProductsPage extends ProductsPageBase {


    @FindBy(xpath = "//input[@id='search_product']")
    private ExtendedWebElement searchProductInput;

    @FindBy(xpath = "//button[@id='submit_search']")

    private ExtendedWebElement submitSearchButton;


    public ProductsPage(WebDriver driver) {
        super(driver);
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

}
