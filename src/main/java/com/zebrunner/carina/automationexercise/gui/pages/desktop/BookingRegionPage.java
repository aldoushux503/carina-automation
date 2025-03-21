package com.zebrunner.carina.automationexercise.gui.pages.desktop;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class BookingRegionPage extends AbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = "//div[contains(@class, 'destination-title')]//h1 | //div[contains(@class, 'region-header')]//h1")
    private ExtendedWebElement regionTitle;

    @FindBy(xpath = "//div[contains(@class, 'destination-description')] | //div[contains(@class, 'region-description')]")
    private ExtendedWebElement aboutSection;

    @FindBy(xpath = "//div[contains(@class, 'explore-container')]//button | //div[contains(@class, 'region-explore')]//button")
    private ExtendedWebElement exploreButton;

    @FindBy(xpath = "//div[contains(@class, 'property-card')]")
    private ExtendedWebElement propertyCard;

    public BookingRegionPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(regionTitle);
    }

    public String getRegionTitle() {
        return regionTitle.getText();
    }

    public String getAboutSectionText() {
        return aboutSection.getText();
    }

    public String getExploreButtonText() {
        return exploreButton.getText();
    }

    public boolean arePropertiesDisplayed() {
        return propertyCard.isElementPresent() && propertyCard.isVisible();
    }
}