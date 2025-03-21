package com.zebrunner.carina.automationexercise.gui.pages.common;

import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;

public abstract class WikipediaHomePageBase extends AbstractPage {

    public WikipediaHomePageBase(WebDriver driver) {
        super(driver);
    }



    public abstract String getSearchPlaceholder();

    public abstract boolean isLogoPresent();


    public abstract String getLogoText();


    public abstract String getSearchButtonText();

}