package com.zebrunner.carina.automationexercise.gui.pages.desktop;

import com.zebrunner.carina.automationexercise.gui.pages.common.CartPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.ProductDetailPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = ProductDetailPageBase.class)
public class ProductDetailPage extends ProductDetailPageBase {

    @FindBy(xpath = "//div[@class='product-information']")
    private ExtendedWebElement productInformation;

    @FindBy(xpath = "//div[@class='product-information']/h2")
    private ExtendedWebElement productName;

    @FindBy(xpath = "//div[@class='product-information']/span/span")
    private ExtendedWebElement productPrice;

    @FindBy(id = "quantity")
    private ExtendedWebElement quantityInput;

    @FindBy(xpath = "//button[@type='button' and contains(text(), 'Add to cart')]")
    private ExtendedWebElement addToCartButton;

    @FindBy(xpath = "//div[@class='modal-content']//a[contains(text(), 'View Cart')]")
    private ExtendedWebElement viewCartButton;

    @FindBy(xpath = "//div[@class='modal-content']//button[contains(text(), 'Continue Shopping')]")
    private ExtendedWebElement continueShoppingButton;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(productInformation);
    }

    @Override
    public boolean isProductDetailPageOpened() {
        return productInformation.isElementPresent();
    }

    @Override
    public String getProductName() {
        return productName.getText();
    }

    @Override
    public String getProductPrice() {
        return productPrice.getText();
    }

    @Override
    public void setQuantity(int quantity) {
        quantityInput.clear();
        quantityInput.type(String.valueOf(quantity));
    }

    @Override
    public int getQuantity() {
        return Integer.parseInt(quantityInput.getAttribute("value"));
    }

    @Override
    public void clickAddToCart() {
        addToCartButton.click();
        waitUntil(d -> viewCartButton.isElementPresent(), 10);
    }

    @Override
    public CartPageBase clickViewCart() {
        viewCartButton.click();
        return initPage(getDriver(), CartPageBase.class);
    }

    @Override
    public void clickContinueShopping() {
        continueShoppingButton.click();
    }
}