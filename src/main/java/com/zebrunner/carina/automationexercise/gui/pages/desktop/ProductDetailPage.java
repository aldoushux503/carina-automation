package com.zebrunner.carina.automationexercise.gui.pages.desktop;

import com.zebrunner.carina.automationexercise.gui.pages.common.CartPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.ProductDetailPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = ProductDetailPageBase.class)
public class ProductDetailPage extends ProductDetailPageBase {

    // Element declarations with descriptive names
    @FindBy(xpath = "//div[@class='product-information']")
    private ExtendedWebElement productInformationBlock;

    @FindBy(xpath = "//div[@class='product-information']/h2")
    private ExtendedWebElement productNameText;

    @FindBy(xpath = "//div[@class='product-information']/span/span")
    private ExtendedWebElement productPriceText;

    @FindBy(id = "quantity")
    private ExtendedWebElement quantityInputField;

    @FindBy(xpath = "//button[@class='btn btn-default cart' and @type='button']")
    private ExtendedWebElement addToCartButton;

    @FindBy(xpath = "//p[@class='text-center']//a[contains(@href, 'view_cart')]")
    private ExtendedWebElement viewCartButton;

    @FindBy(xpath = "//div[@class='modal-content']//button[contains(text(), 'Continue Shopping')]")
    private ExtendedWebElement continueShoppingButton;

    // Review section elements
    @FindBy(xpath = "//div[@id='reviews']")
    private ExtendedWebElement reviewSection;

    @FindBy(xpath = "//a[contains(@href, '#reviews') and contains(text(), 'Write Your Review')]")
    private ExtendedWebElement writeYourReviewTab;

    @FindBy(id = "name")
    private ExtendedWebElement reviewNameInputField;

    @FindBy(id = "email")
    private ExtendedWebElement reviewEmailInputField;

    @FindBy(id = "review")
    private ExtendedWebElement reviewTextInputField;

    @FindBy(id = "button-review")
    private ExtendedWebElement submitReviewButton;

    @FindBy(id = "review-section")
    private ExtendedWebElement reviewSuccessSection;

    @FindBy(xpath = "//div[@id='review-section']//div[contains(@class, 'alert-success')]")
    private ExtendedWebElement reviewSuccessMessageText;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(productInformationBlock);
    }

    @Override
    public boolean isProductDetailPageOpened() {
        return productInformationBlock.isElementPresent();
    }

    @Override
    public String getProductName() {
        return productNameText.getText();
    }

    @Override
    public String getProductPrice() {
        return productPriceText.getText();
    }

    @Override
    public void setQuantity(int quantity) {
        quantityInputField.type(String.valueOf(quantity));
    }

    @Override
    public int getQuantity() {
        return Integer.parseInt(quantityInputField.getAttribute("value"));
    }

    @Override
    public void clickAddToCart() {
        addToCartButton.click();
        waitUntil(d -> viewCartButton.isElementPresent(), 3);
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

    @Override
    public boolean isReviewSectionVisible() {
        return writeYourReviewTab.isElementPresent() &&
                writeYourReviewTab.isVisible() &&
                reviewSection.isElementPresent();
    }

    @Override
    public void inputReviewName(String name) {
        reviewNameInputField.type(name);
    }

    @Override
    public void inputReviewEmail(String email) {
        reviewEmailInputField.type(email);
    }

    @Override
    public void inputReviewText(String reviewText) {
        reviewTextInputField.type(reviewText);
    }

    @Override
    public void submitReview() {
        submitReviewButton.scrollTo();
        submitReviewButton.click();
        waitUntil(d -> isReviewSuccessMessageVisible(), 3);
    }

    @Override
    public boolean isReviewSuccessMessageVisible() {
        return !reviewSuccessSection.getAttribute("class").contains("hide") &&
                reviewSuccessMessageText.isElementPresent() &&
                reviewSuccessMessageText.getText().contains("Thank you for your review");
    }
}