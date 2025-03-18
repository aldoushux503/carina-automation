package com.zebrunner.carina.automationexercise.gui.pages.desktop;

import com.zebrunner.carina.automationexercise.gui.components.topMenu.TopMenu;
import com.zebrunner.carina.automationexercise.gui.components.topMenu.TopMenuBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.ContactUsPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.HomePageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.ProductsPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = HomePageBase.class)
public class HomePage extends HomePageBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = "//div[contains(@class, 'shop-menu')]")
    private TopMenu topMenu;

    @FindBy(xpath = "//div[contains(@class, 'features_items')]")
    private ExtendedWebElement featuresItems;
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
    public TopMenu getTopMenu(){
        return topMenu;
    }
}
