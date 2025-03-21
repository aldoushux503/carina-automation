package com.zebrunner.carina.automationexercise.gui.pages.desktop;

import com.zebrunner.carina.automationexercise.gui.pages.common.WikipediaHomePageBase;
import com.zebrunner.carina.utils.config.Configuration;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.config.WebDriverConfiguration;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = WikipediaHomePageBase.class)
public class WikipediaHomePage extends WikipediaHomePageBase {

    @FindBy(xpath = "//div[@id='js-lang-lists']//a")
    private List<ExtendedWebElement> langList;

    @FindBy(id = "js-lang-list-button")
    private ExtendedWebElement langListBtn;

    @FindBy(id = "searchInput")
    private ExtendedWebElement searchInput;

    @FindBy(css = ".pure-button.pure-button-primary-progressive")
    private ExtendedWebElement searchButton;

    @FindBy(css = ".central-featured-logo")
    private ExtendedWebElement logo;

    @FindBy(css = ".central-textlogo")
    private ExtendedWebElement textLogo;

    public WikipediaHomePage(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL("https://www.wikipedia.org/");
    }

    public WikipediaLocalePage goToWikipediaLocalePage() {
        openLangList();
        if (!langList.isEmpty()) {
            for (ExtendedWebElement languageBtn : langList) {
                String localeStr = Configuration.getRequired(WebDriverConfiguration.Parameter.LOCALE);
                Locale locale = parseLocale(localeStr);
                if (languageBtn.getAttribute("lang").equals(locale.getLanguage())) {
                    languageBtn.click();
                    return new WikipediaLocalePage(getDriver());
                }
            }
        }
        throw new RuntimeException("No language ref was found");
    }

    public void openLangList() {
        langListBtn.clickIfPresent();
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
    public String getSearchButtonText() {
        return searchButton.getText();
    }

    private Locale parseLocale(String localeToParse) {
        String[] localeSettings = localeToParse.trim().split("_");
        String lang, country = "";
        lang = localeSettings[0];
        if (localeSettings.length > 1) {
            country = localeSettings[1];
        }

        return new Locale(lang, country);
    }
}