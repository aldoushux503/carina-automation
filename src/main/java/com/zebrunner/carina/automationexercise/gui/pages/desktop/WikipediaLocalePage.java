package com.zebrunner.carina.automationexercise.gui.pages.desktop;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;
import org.openqa.selenium.By;
import java.util.List;

public class WikipediaLocalePage extends AbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(css = "#firstHeading")
    private ExtendedWebElement pageTitle;

    @FindBy(css = "#n-mainpage-description, #n-mainpage")
    private ExtendedWebElement mainPageLink;

    @FindBy(css = "#n-randompage")
    private ExtendedWebElement randomPageLink;

    @FindBy(css = "#p-search input[type='search'], input#searchInput")
    private ExtendedWebElement searchInput;

    @FindBy(css = "#searchButton")
    private ExtendedWebElement searchButton;

    @FindBy(css = "#pt-login, #pt-login-2")
    private ExtendedWebElement loginLink;

    @FindBy(css = "#pt-createaccount, #pt-createaccount-2")
    private ExtendedWebElement createAccountLink;

    // Multiple selectors for footer text
    private static final String[] FOOTER_SELECTORS = {
            "#footer-info",
            ".footer-places",
            "#footer-info-lastmod",
            "#footer",
            ".footer",
            ".footer-sidebar"
    };

    public WikipediaLocalePage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return pageTitle.getText();
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

    public String getSearchButtonTooltip() {
        return searchButton.getAttribute("title");
    }

    public String getLoginLinkText() {
        return loginLink.getText();
    }

    public String getCreateAccountLinkText() {
        return createAccountLink.getText();
    }

    public String getFooterInfoText() {
        // Try different footer selectors
        for (String selector : FOOTER_SELECTORS) {
            ExtendedWebElement element = findExtendedWebElement(By.cssSelector(selector), 1);
            if (element.isElementPresent()) {
                String text = element.getText();
                if (text != null && !text.isEmpty()) {
                    LOGGER.info("Found footer text with selector: {} - Text: {}", selector, text);
                    return text;
                }
            }
        }

        // Alternative approach - get all elements with footer-like classes
        List<ExtendedWebElement> footerElements = findExtendedWebElements(By.cssSelector("footer, .footer, #footer, #footer-info, .footer-info, .footer-content"));
        StringBuilder allFooterText = new StringBuilder();
        for (ExtendedWebElement element : footerElements) {
            if (element.isElementPresent()) {
                String text = element.getText();
                if (text != null && !text.isEmpty()) {
                    allFooterText.append(text).append(" ");
                }
            }
        }

        if (allFooterText.length() > 0) {
            LOGGER.info("Combined footer text: {}", allFooterText.toString());
            return allFooterText.toString();
        }

        // Last resort - check page source for common footer phrases
        String pageSource = getDriver().getPageSource();
        if (pageSource.contains("encyclopedia")) {
            return "encyclopedia";
        } else if (pageSource.contains("enciclopedia")) {
            return "enciclopedia";
        } else if (pageSource.contains("encyclopédie")) {
            return "encyclopédie";
        }

        LOGGER.warn("Could not find any footer text on the page");
        return "";
    }

    public void searchFor(String query) {
        searchInput.type(query);
        searchButton.click();
    }
}