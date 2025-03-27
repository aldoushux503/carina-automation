package com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise;

import com.zebrunner.carina.automationexercise.gui.components.topMenu.TopMenu;
import com.zebrunner.carina.automationexercise.gui.pages.desktop.automationexercise.CartPage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public abstract class HomePageBase extends AbstractPage {

    @FindBy(xpath = "//p[@class='fc-button-label']")
    private ExtendedWebElement acceptCookie;
    public HomePageBase(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
    }

    public abstract TopMenu getTopMenu();

    public abstract ContactUsPageBase getContactUsPage();
    public abstract ProductsPageBase getProductsPage();

    public abstract CartPage getCartPage();

    public abstract void scrollToSubscription();

    public abstract boolean isSubscriptionSectionVisible();

    public abstract void subscribeWithEmail(String email);

    public abstract boolean isSubscriptionSuccessMessageVisible();

    public abstract boolean isRecommendedItemsSectionVisible();
    public abstract void scrollToRecommendedItems();
    public abstract String getRecommendedItemName(int index);
    public abstract void addRecommendedItemToCart(int index);
    public abstract CartPageBase clickViewCartFromModal();

    @Override
    public void open() {
        super.open();
        acceptCookie.clickIfPresent(5);
    }
}
