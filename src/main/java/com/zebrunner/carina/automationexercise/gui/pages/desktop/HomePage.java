package com.zebrunner.carina.automationexercise.gui.pages.desktop;

import com.zebrunner.carina.automationexercise.gui.components.ProductInfo;
import com.zebrunner.carina.automationexercise.gui.components.topMenu.TopMenu;
import com.zebrunner.carina.automationexercise.gui.components.topMenu.TopMenuBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.*;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = HomePageBase.class)
public class HomePage extends HomePageBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = "//div[contains(@class, 'shop-menu')]")
    private TopMenu topMenu;

    @FindBy(xpath = "//div[contains(@class, 'features_items')]")
    private ExtendedWebElement featuresItems;

    @FindBy(xpath = "//h2[contains(text(),'Subscription')]")
    private ExtendedWebElement subscriptionTitle;

    @FindBy(id = "susbscribe_email")
    private ExtendedWebElement subscribeEmailField;

    @FindBy(id = "subscribe")
    private ExtendedWebElement subscribeButton;

    @FindBy(xpath = "//div[@class='alert-success alert']")
    private ExtendedWebElement subscriptionSuccessMessage;


    public HomePage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(featuresItems);
    }

    @Override
    public ContactUsPage getContactUsPage() {
        return getTopMenu()
                .openContactUsPage();
    }

    @Override
    public ProductsPage getProductsPage() {
        return getTopMenu()
                .openProductsPage();
    }

    @Override
    public CartPage getCartPage() {
        return getTopMenu()
                .openCartPage();
    }

    @Override
    public TopMenu getTopMenu() {
        return topMenu;
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

}
