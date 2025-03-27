package com.zebrunner.carina.automationexercise.web.wikipedia;

import com.zebrunner.carina.automationexercise.gui.pages.desktop.wikipedia.WikipediaHomePage;
import com.zebrunner.carina.automationexercise.gui.pages.desktop.wikipedia.WikipediaLocalePage;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.utils.R;
import com.zebrunner.carina.utils.resources.L10N;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WebWikipediaLocalizationTest implements IAbstractTest {

    private WikipediaHomePage homePage;

    @BeforeMethod
    public void setUp() {
        homePage = new WikipediaHomePage(getDriver());
        homePage.open();
    }

    @Test(dataProvider = "languages")
    public void testMainPageTitleLocalization(String locale, String language) {
        R.CONFIG.put("locale", locale, true);
        L10N.load();

        WikipediaLocalePage localePage = homePage.goToWikipediaLocalePage(getDriver());

        String expectedTitle = L10N.getText("LocalePage.mainPageTitle");
        String actualTitle = localePage.getPageTitle();

        Assert.assertEquals(actualTitle, expectedTitle,
                "Main page title is not correctly localized in " + language + ". Expected: " + expectedTitle + ", Actual: " + actualTitle);
    }

    @Test(dataProvider = "languages")
    public void testNavigationElementsLocalization(String locale, String language) {
        R.CONFIG.put("locale", locale);

        WikipediaLocalePage localePage = homePage.goToWikipediaLocalePage(getDriver());
        Assert.assertTrue(localePage.isMainPageLinkTextEqualTo(R.TESTDATA.get("LocalePage.mainPageLink")),
                "Main page link text is not correctly localized in " + language);
        Assert.assertTrue(localePage.isRandomPageLinkTextEqualTo(R.TESTDATA.get("LocalePage.randomPageLink")),
                "Random page link text is not correctly localized in " + language);
    }

    @Test(dataProvider = "languages")
    public void testSearchFunctionalityLocalization(String locale, String language) {
        R.CONFIG.put("locale", locale, true);
        L10N.load();

        WikipediaLocalePage localePage = homePage.goToWikipediaLocalePage(getDriver());

        String expectedPlaceholder = L10N.getText("LocalePage.searchPlaceholder");
        String actualPlaceholder = localePage.getSearchPlaceholder();

        Assert.assertEquals(actualPlaceholder, expectedPlaceholder,
                "Search placeholder is not correctly localized in " + language + ". Expected: " + expectedPlaceholder + ", Actual: " + actualPlaceholder);
    }

    @Test(dataProvider = "languages")
    public void testAccountLinksLocalization(String locale, String language) {
        R.CONFIG.put("locale", locale);

        WikipediaLocalePage localePage = homePage.goToWikipediaLocalePage(getDriver());
        Assert.assertTrue(localePage.isLoginLinkTextEqualTo(R.TESTDATA.get("LocalePage.loginLink")),
                "Login link text is not correctly localized in " + language);
        Assert.assertTrue(localePage.isCreateAccountLinkTextEqualTo(R.TESTDATA.get("LocalePage.createAccountLink")),
                "Create account link text is not correctly localized in " + language);
    }

    @Test(dataProvider = "languages")
    public void testLanguageSwitching(String locale, String language) {
        R.CONFIG.put("locale", locale);
        WikipediaLocalePage localePage = homePage.goToWikipediaLocalePage(getDriver());

        boolean correctLanguage = localePage.isCurrentLanguage(language);
        Assert.assertTrue(correctLanguage,
                "Language was not switched correctly to " + language + ". Current URL: " + getDriver().getCurrentUrl());

        String langAttribute = localePage.getLangAttribute();
        String expectedLangCode = locale.substring(0, 2).toLowerCase();
        Assert.assertEquals(langAttribute, expectedLangCode,
                "HTML lang attribute doesn't match expected language. Expected: " + expectedLangCode + ", Actual: " + langAttribute);
    }

    @DataProvider(name = "languages")
    public Object[][] provideLanguages() {
        return new Object[][]{
                {"en_US", "English"},
                {"fr_FR", "Français"},
                {"es_ES", "Español"}
        };
    }
}