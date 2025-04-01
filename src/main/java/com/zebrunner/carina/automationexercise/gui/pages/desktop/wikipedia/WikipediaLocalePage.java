package com.zebrunner.carina.automationexercise.gui.pages.desktop.wikipedia;

import com.zebrunner.carina.automationexercise.gui.pages.common.wikipedia.WikipediaHomePageBase;
import com.zebrunner.carina.utils.R;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class WikipediaLocalePage extends AbstractPage {

    @FindBy(xpath = "//h1[@id='firstHeading']//span")
    private ExtendedWebElement pageTitleSpan;

    @FindBy(xpath = "//h1[@id='firstHeading']")
    private ExtendedWebElement pageTitleContainer;

    @FindBy(id = "n-mainpage-description")
    private ExtendedWebElement mainPageLink;

    @FindBy(id = "n-randompage")
    private ExtendedWebElement randomPageLink;

    @FindBy(id = "searchInput")
    private ExtendedWebElement searchInput;

    @FindBy(id = "searchButton")
    private ExtendedWebElement searchButton;

    @FindBy(xpath = "//div[@id='footer-info']")
    private ExtendedWebElement footerInfo;

    @FindBy(xpath = "//li[@id='pt-login']//a")
    private ExtendedWebElement loginLink;

    @FindBy(xpath = "//li[@id='pt-createaccount']//a")
    private ExtendedWebElement createAccountLink;

    @FindBy(id = "p-lang-label")
    private ExtendedWebElement languagePortlet;

    @FindBy(xpath = "//html")
    private ExtendedWebElement htmlElement;

    public WikipediaLocalePage(WebDriver driver) {
        super(driver);
    }


    public String getPageTitle() {
        return getDriver().getTitle();
    }

    public String getMainPageLinkText() {
        return mainPageLink.getText();
    }

    public String getRandomPageLinkText() {
        return randomPageLink.getText();
    }

    public String getSearchPlaceholder() {
        return searchInput.getAttribute("placeholder");
    }

    public String getSearchTooltip() {
        return searchButton.getAttribute("title");
    }

    public String getLoginLinkText() {
        return loginLink.getText();
    }

    public String getCreateAccountLinkText() {
        return createAccountLink.getText();
    }


    public boolean isMainPageLinkTextEqualTo(String expectedText) {
        return getMainPageLinkText().equals(expectedText);
    }

    public boolean isRandomPageLinkTextEqualTo(String expectedText) {
        return getRandomPageLinkText().equals(expectedText);
    }

    public boolean isLoginLinkTextEqualTo(String expectedText) {
        return getLoginLinkText().equals(expectedText);
    }

    public boolean isCreateAccountLinkTextEqualTo(String expectedText) {
        return getCreateAccountLinkText().equals(expectedText);
    }

    public boolean isCurrentLanguage(String language) {
        String languageCode = language.toLowerCase().substring(0, 2);
        String currentUrl = getDriver().getCurrentUrl();
        return currentUrl.contains("/" + languageCode + ".wikipedia.org/") ||
                (language.equalsIgnoreCase("English") && currentUrl.contains("en.wikipedia.org"));
    }

    public String getLangAttribute() {
        return htmlElement.getAttribute("lang");
    }
}