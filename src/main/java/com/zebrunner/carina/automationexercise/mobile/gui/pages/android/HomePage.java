package com.zebrunner.carina.automationexercise.mobile.gui.pages.android;

import com.zebrunner.carina.automationexercise.mobile.gui.pages.common.HomePageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = HomePageBase.class)
public class HomePage extends HomePageBase {

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Active\"]")
    private ExtendedWebElement activeButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void clickActiveButton() {
        activeButton.click();
    }


}
