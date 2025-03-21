package com.zebrunner.carina.automationexercise.web;

import com.zebrunner.agent.core.annotation.TestLabel;
import com.zebrunner.carina.automationexercise.gui.pages.common.WikipediaHomePageBase;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;

import com.zebrunner.carina.utils.R;
import com.zebrunner.carina.utils.config.Configuration;
import com.zebrunner.carina.utils.resources.L10N;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Locale;

public class WikipediaLocalizationTest implements IAbstractTest {


    @BeforeClass
    public void testLocaleLoad() {
        Locale locale = L10N.getLocale();
        String loadedLocale = locale.getLanguage() + "_" + locale.getCountry();
        String configLocale = R.CONFIG.get("locale");
        Assert.assertEquals(loadedLocale, configLocale);
    }

    @Test
    @MethodOwner(owner = "carina")
    @TestLabel(name = "feature", value = "l10n")
    public void testSearchFunctionalityLocalization() {
        WikipediaHomePageBase homePage = initPage(getDriver(), WikipediaHomePageBase.class);
        homePage.open();

        SoftAssert softAssert = new SoftAssert();

        String actualPlaceholder = homePage.getSearchPlaceholder();
        String expectedPlaceholder = L10N.getText("HomePage.searchPlaceholder");
        softAssert.assertEquals(actualPlaceholder, expectedPlaceholder,
                "Search placeholder is not localized correctly");

        String actualSearchButton = homePage.getSearchButtonText();
        String expectedSearchButton = L10N.getText("HomePage.searchButton");
        softAssert.assertEquals(actualSearchButton, expectedSearchButton,
                "Search button text is not localized correctly");

        softAssert.assertAll();
        L10N.assertAll();
    }

    @Test
    @MethodOwner(owner = "carina")
    @TestLabel(name = "feature", value = "l10n")
    public void testLogoTextLocalization() {
        WikipediaHomePageBase homePage = initPage(getDriver(), WikipediaHomePageBase.class);
        homePage.open();

        Assert.assertTrue(homePage.isLogoPresent(), "Wikipedia logo is not present");

        String actualLogoText = homePage.getLogoText();
        String expectedLogoText = L10N.getText("HomePage.logoText");
        Assert.assertEquals(actualLogoText, expectedLogoText,
                "Logo text is not localized correctly");

        L10N.assertAll();
    }

    @Test
    @MethodOwner(owner = "carina")
    @TestLabel(name = "feature", value = "l10n")
    public void testNavigationMenuLocalization() {
        WikipediaHomePageBase homePage = initPage(getDriver(), WikipediaHomePageBase.class);
        homePage.open();

        SoftAssert softAssert = new SoftAssert();

        String actualMainPageText = homePage.getMainPageLinkText();
        String expectedMainPageText = L10N.getText("HomePage.mainPageLink");
        softAssert.assertEquals(actualMainPageText, expectedMainPageText,
                "Main page link text is not localized correctly");

        String actualRandomPageText = homePage.getRandomPageLinkText();
        String expectedRandomPageText = L10N.getText("HomePage.randomPageLink");
        softAssert.assertEquals(actualRandomPageText, expectedRandomPageText,
                "Random page link text is not localized correctly");

        softAssert.assertAll();
        L10N.assertAll();
    }

    @Test
    @MethodOwner(owner = "carina")
    @TestLabel(name = "feature", value = "l10n")
    public void testUserMenuLocalization() {
        WikipediaHomePageBase homePage = initPage(getDriver(), WikipediaHomePageBase.class);
        homePage.open();

        SoftAssert softAssert = new SoftAssert();

        String actualLoginText = homePage.getLoginLinkText();
        String expectedLoginText = L10N.getText("HomePage.loginLink");
        softAssert.assertEquals(actualLoginText, expectedLoginText,
                "Login link text is not localized correctly");

        String actualCreateAccountText = homePage.getCreateAccountLinkText();
        String expectedCreateAccountText = L10N.getText("HomePage.createAccountLink");
        softAssert.assertEquals(actualCreateAccountText, expectedCreateAccountText,
                "Create account link text is not localized correctly");

        softAssert.assertAll();
        L10N.assertAll();
    }

    @Test
    @MethodOwner(owner = "carina")
    @TestLabel(name = "feature", value = "l10n")
    public void testFooterLocalization() {
        WikipediaHomePageBase homePage = initPage(getDriver(), WikipediaHomePageBase.class);
        homePage.open();

        String actualFooterText = homePage.getFooterText();
        String expectedFooterText = L10N.getText("HomePage.footerText");

        // Just check if the footer contains the expected text since footer can have many elements
        Assert.assertTrue(actualFooterText.contains(expectedFooterText),
                "Footer text does not contain the expected localized text");

        L10N.assertAll();
    }
}