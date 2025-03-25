package com.zebrunner.carina.automationexercise.gui.components.topMenu;

import com.zebrunner.carina.automationexercise.gui.pages.common.CartPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.desktop.CartPage;
import com.zebrunner.carina.automationexercise.gui.pages.desktop.ContactUsPage;
import com.zebrunner.carina.automationexercise.gui.pages.desktop.HomePage;
import com.zebrunner.carina.automationexercise.gui.pages.desktop.ProductsPage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TopMenu extends TopMenuBase {

    // Element declarations with descriptive names
    @FindBy(xpath = "//a[contains(text(),'Home')]")
    private ExtendedWebElement homeLink;

    @FindBy(xpath = "//a[contains(text(),'Products')]")
    private ExtendedWebElement productsLink;

    @FindBy(xpath = "//a[contains(text(),'Cart')]")
    private ExtendedWebElement cartLink;

    @FindBy(xpath = "//a[contains(text(),'Signup / Login')]")
    private ExtendedWebElement loginSignupLink;

    @FindBy(xpath = "//a[contains(@href, '/contact_us')]")
    private ExtendedWebElement contactUsLink;

    public TopMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    @Override
    public HomePage openHomePage() {
        homeLink.click();
        return new HomePage(driver);
    }

    @Override
    public ContactUsPage openContactUsPage() {
        contactUsLink.click();
        return new ContactUsPage(driver);
    }

    @Override
    public CartPage openCartPage() {
        cartLink.click();
        return new CartPage(driver);
    }

    @Override
    public ProductsPage openProductsPage() {
        productsLink.click();
        return new ProductsPage(driver);
    }
}