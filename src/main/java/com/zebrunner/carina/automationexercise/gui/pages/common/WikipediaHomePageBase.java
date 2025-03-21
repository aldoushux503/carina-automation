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

    public abstract String getMainPageLinkText();

    public abstract String getRandomPageLinkText();

    public abstract String getLoginLinkText();

    public abstract String getCreateAccountLinkText();

    public abstract String getSearchButtonText();

    public abstract String getFooterText();

    public abstract void clickMoreButton();

    public abstract void hoverContribElem();

    public abstract void clickDiscussionBtn();

    public abstract String getDiscussionText();

    public abstract void hoverCreateAccountElem();

    public abstract void hoverWelcomeText();

    public abstract void selectLanguage(String language);
}