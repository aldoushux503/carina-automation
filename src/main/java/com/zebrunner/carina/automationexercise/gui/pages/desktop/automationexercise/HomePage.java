package com.zebrunner.carina.automationexercise.gui.pages.desktop.automationexercise;

import com.zebrunner.carina.automationexercise.gui.components.topMenu.TopMenu;
import com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise.CartPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise.HomePageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = HomePageBase.class)
public class HomePage extends HomePageBase {

    private static final String RECOMMENDED_ITEMS_BASE = "//div[@class='recommended_items']//div[@class='item active']//div[contains(@class, 'col-sm-4')]";

    @FindBy(xpath = "//div[contains(@class, 'shop-menu')]")
    private TopMenu topMenu;

    @FindBy(xpath = "//div[contains(@class, 'features_items')]")
    private ExtendedWebElement featuresItemsSection;

    @FindBy(xpath = "//h2[contains(text(),'Subscription')]")
    private ExtendedWebElement subscriptionTitleText;

    @FindBy(id = "susbscribe_email")
    private ExtendedWebElement subscribeEmailField;

    @FindBy(id = "subscribe")
    private ExtendedWebElement subscribeButton;

    @FindBy(xpath = "//div[@class='alert-success alert']")
    private ExtendedWebElement subscriptionSuccessMessageText;

    @FindBy(xpath = "//div[@class='recommended_items']")
    private ExtendedWebElement recommendedItemsSection;

    @FindBy(xpath = "//h2[contains(text(),'recommended items')]")
    private ExtendedWebElement recommendedItemsTitleText;

    @FindBy(xpath = "//div[@class='modal-content']//a[contains(@href, 'view_cart')]")
    private ExtendedWebElement viewCartModalButton;

    @FindBy(xpath = "//p[@class='fc-button-label']")
    private ExtendedWebElement acceptCookieButton;

    @FindBy(xpath = RECOMMENDED_ITEMS_BASE)
    private List<ExtendedWebElement> recommendedItems;

    @FindBy(xpath = RECOMMENDED_ITEMS_BASE + "//div[@class='productinfo text-center']/p")
    private List<ExtendedWebElement> recommendedItemNames;

    @FindBy(xpath = RECOMMENDED_ITEMS_BASE + "//a[@class='btn btn-default add-to-cart']")
    private List<ExtendedWebElement> recommendedItemAddToCartButtons;

    public HomePage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(featuresItemsSection);
    }

    @Override
    public ContactUsPage getContactUsPage() {
        return getTopMenu().openContactUsPage();
    }

    @Override
    public ProductsPage getProductsPage() {
        return getTopMenu().openProductsPage();
    }

    @Override
    public CartPage getCartPage() {
        return getTopMenu().openCartPage();
    }

    @Override
    public TopMenu getTopMenu() {
        return topMenu;
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
        return subscriptionSuccessMessageText.isElementPresent() &&
                subscriptionSuccessMessageText.getText().contains("You have been successfully subscribed!");
    }

    @Override
    public boolean isRecommendedItemsSectionVisible() {
        return recommendedItemsTitleText.isElementPresent() &&
                recommendedItemsTitleText.getText().equalsIgnoreCase("RECOMMENDED ITEMS");
    }

    @Override
    public void scrollToRecommendedItems() {
        recommendedItemsSection.scrollTo();
    }

    @Override
    public String getRecommendedItemName(int index) {
        validateRecommendedItemIndex(index);
        return recommendedItemNames.get(index).getText();
    }

    @Override
    public void addRecommendedItemToCart(int index) {
        validateRecommendedItemIndex(index);
        recommendedItemAddToCartButtons.get(index).click();
    }

    @Override
    public CartPageBase clickViewCartFromModal() {
        waitUntil(d -> viewCartModalButton.isElementPresent(), 3);
        viewCartModalButton.click();
        return initPage(getDriver(), CartPageBase.class);
    }

    @Override
    public void open() {
        super.open();
        acceptCookieButton.clickIfPresent(3);
    }

    private void validateRecommendedItemIndex(int index) {
        if (index < 0 || index >= recommendedItems.size()) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
    }
}