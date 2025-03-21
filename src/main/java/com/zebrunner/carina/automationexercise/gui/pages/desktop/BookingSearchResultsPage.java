package com.zebrunner.carina.automationexercise.gui.pages.desktop;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class BookingSearchResultsPage extends AbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = "//div[@data-testid='property-card-container' or contains(@class, 'property-card')]")
    private ExtendedWebElement firstPropertyCard;

    @FindBy(xpath = "//h1[contains(@class, 'title')]")
    private ExtendedWebElement searchResultsTitle;

    @FindBy(xpath = "//div[contains(@class, 'filteroptions')]//h3[1] | //div[contains(@class, 'filter-section')]//h3[1]")
    private ExtendedWebElement filterSectionLabel;

    @FindBy(xpath = "//div[contains(@class, 'sort-by')]//span[1]")
    private ExtendedWebElement sortByLabel;

    @FindBy(xpath = "//div[contains(@class, 'results-stats')]")
    private ExtendedWebElement resultsStatsLabel;

    public BookingSearchResultsPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(firstPropertyCard);
    }

    public String getSearchResultsTitle() {
        return searchResultsTitle.getText();
    }

    public String getFilterSectionLabel() {
        return filterSectionLabel.getText();
    }

    public String getSortByLabel() {
        return sortByLabel.getText();
    }

    public String getResultsStatsLabel() {
        return resultsStatsLabel.getText();
    }

    public boolean areResultsDisplayed() {
        return firstPropertyCard.isElementPresent() && firstPropertyCard.isVisible();
    }
}