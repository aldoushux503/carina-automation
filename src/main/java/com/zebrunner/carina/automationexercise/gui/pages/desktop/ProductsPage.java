package com.zebrunner.carina.automationexercise.gui.pages.desktop;

import com.zebrunner.carina.automationexercise.gui.pages.common.ProductsPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = ProductsPageBase.class)
public class ProductsPage extends ProductsPageBase {

    @FindBy(xpath = "//div[@class='features_items']")
    private List<ExtendedWebElement> featuresItemsBox;

    @FindBy(xpath = "//input[@id='search_product']")
    private ExtendedWebElement searchProductInput;

    @FindBy(xpath = "//button[@id='submit_search']")

    private ExtendedWebElement submitSearchButton;


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

    @FindBy(xpath = "//div[@class='features_items']//div[@class='productinfo']")
    private List<ExtendedWebElement> searchResults;

    @Override
    public boolean areSearchResultsVisible(String searchTerm) {

        if (featuresItemsBox.isEmpty()) {
            return false;
        }

        System.out.println(featuresItemsBox.size());
        for (ExtendedWebElement productName : featuresItemsBox) {
            String name = productName.getText().toLowerCase();
            if (!name.contains(searchTerm.toLowerCase())) {
                return false;
            }
        }

        return true;
    }

}
