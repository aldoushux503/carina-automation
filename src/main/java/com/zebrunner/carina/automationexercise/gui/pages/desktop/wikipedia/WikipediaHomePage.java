package com.zebrunner.carina.automationexercise.gui.pages.desktop.wikipedia;

import com.zebrunner.carina.automationexercise.gui.pages.common.wikipedia.WikipediaHomePageBase;
import com.zebrunner.carina.utils.config.Configuration;
import com.zebrunner.carina.webdriver.config.WebDriverConfiguration;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Locale;

public class WikipediaHomePage extends WikipediaHomePageBase {

    @FindBy(xpath = "//div[@id='js-lang-lists']//a")
    private List<ExtendedWebElement> langList;

    @FindBy(id = "js-lang-list-button")
    private ExtendedWebElement langListBtn;

    public WikipediaHomePage(WebDriver driver) {
        super(driver);
        setPageAbsoluteURL("https://www.wikipedia.org/");
        setPageOpeningStrategy(PageOpeningStrategy.BY_URL);
    }

    public WikipediaLocalePage goToWikipediaLocalePage(WebDriver driver) {
        openLangList();
        if (!langList.isEmpty()) {
            for (ExtendedWebElement languageBtn : langList) {
                String localeStr = Configuration.getRequired(WebDriverConfiguration.Parameter.LOCALE);
                Locale locale = parseLocale(localeStr);
                if (languageBtn.getAttribute("lang").equals(locale.getLanguage())) {
                    languageBtn.click();
                    return new WikipediaLocalePage(driver);
                }
            }
        }
        throw new RuntimeException("No language ref was found");
    }

    public void openLangList() {
        langListBtn.clickIfPresent();
    }

    private Locale parseLocale(String localeToParse) {
        String[] localeSetttings = localeToParse.trim().split("_");
        String lang, country = "";
        lang = localeSetttings[0];
        if (localeSetttings.length > 1) {
            country = localeSetttings[1];
        }
        return new Locale(lang, country);
    }
}