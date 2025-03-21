package com.zebrunner.carina.automationexercise.gui.pages.desktop;

import com.zebrunner.carina.automationexercise.gui.pages.common.WikipediaHomePageBase;
import com.zebrunner.carina.utils.config.Configuration;
import com.zebrunner.carina.webdriver.config.WebDriverConfiguration;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Locale;

public class WikipediaHomePage extends WikipediaHomePageBase {

    @FindBy(id = "js-link-box-en")
    private ExtendedWebElement englishLink;

    @FindBy(id = "js-link-box-es")
    private ExtendedWebElement spanishLink;

    @FindBy(id = "js-link-box-fr")
    private ExtendedWebElement frenchLink;

    @FindBy(id = "searchInput")
    private ExtendedWebElement searchInput;

    @FindBy(css = ".pure-button.pure-button-primary-progressive")
    private ExtendedWebElement searchButton;

    @FindBy(css = ".central-featured-logo")
    private ExtendedWebElement logo;

    @FindBy(css = ".central-textlogo")
    private ExtendedWebElement textLogo;

    @FindBy(css = "#n-mainpage-description")
    private ExtendedWebElement mainPageLink;

    @FindBy(css = "#n-randompage")
    private ExtendedWebElement randomPageLink;

    @FindBy(css = "#pt-login-2")
    private ExtendedWebElement loginLink;

    @FindBy(css = "#pt-createaccount")
    private ExtendedWebElement createAccountLink;

    @FindBy(css = ".vector-menu-more")
    private ExtendedWebElement moreButton;

    @FindBy(css = ".mw-list-item-js")
    private ExtendedWebElement contributionsElement;

    @FindBy(css = "#ca-talk")
    private ExtendedWebElement discussionButton;

    @FindBy(css = ".welcome-title")
    private ExtendedWebElement welcomeText;

    @FindBy(css = ".footer-sidebar-content")
    private ExtendedWebElement footerText;

    public WikipediaHomePage(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL("https://www.wikipedia.org");
    }

    @Override
    public String getSearchPlaceholder() {
        return searchInput.getAttribute("placeholder");
    }

    @Override
    public boolean isLogoPresent() {
        return logo.isElementPresent();
    }

    @Override
    public String getLogoText() {
        return textLogo.getText();
    }

    @Override
    public String getMainPageLinkText() {
        return mainPageLink.getText();
    }

    @Override
    public String getRandomPageLinkText() {
        return randomPageLink.getText();
    }

    @Override
    public String getLoginLinkText() {
        return loginLink.getText();
    }

    @Override
    public String getCreateAccountLinkText() {
        return createAccountLink.getText();
    }

    @Override
    public String getSearchButtonText() {
        return searchButton.getText();
    }

    @Override
    public String getFooterText() {
        return footerText.getText();
    }

    @Override
    public void clickMoreButton() {
        moreButton.click();
    }

    @Override
    public void hoverContribElem() {
        contributionsElement.hover();
    }

    @Override
    public void clickDiscussionBtn() {
        discussionButton.click();
    }

    @Override
    public String getDiscussionText() {
        return discussionButton.getText();
    }

    @Override
    public void hoverCreateAccountElem() {
        createAccountLink.hover();
    }

    @Override
    public void hoverWelcomeText() {
        welcomeText.hover();
    }

    @Override
    public void selectLanguage(String language) {
        switch (language.toLowerCase()) {
            case "english":
                englishLink.click();
                break;
            case "spanish":
                spanishLink.click();
                break;
            case "french":
                frenchLink.click();
                break;
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }
}