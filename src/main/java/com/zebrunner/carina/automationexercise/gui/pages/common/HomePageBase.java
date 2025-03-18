package com.zebrunner.carina.automationexercise.gui.pages.common;

import com.zebrunner.carina.automationexercise.gui.components.topMenu.TopMenu;
import com.zebrunner.carina.automationexercise.gui.pages.desktop.ContactUsPage;
import com.zebrunner.carina.automationexercise.gui.pages.desktop.ProductsPage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

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

    @Override
    public void open() {
        super.open();
        acceptCookie.clickIfPresent(5);
    }
}
