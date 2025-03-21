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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class WikipediaMultiLanguageTest implements IAbstractTest {

    @DataProvider(name = "languages")
    public Object[][] languages() {
        return new Object[][] {
                {"english", "en_US"},
                {"spanish", "es_ES"},
                {"french", "fr_FR"}
        };
    }

    @Test(dataProvider = "languages")
    @MethodOwner(owner = "carina")
    @TestLabel(name = "feature", value = "l10n")
    public void testLanguageSwitching(String language, String locale) {
        WikipediaHomePageBase homePage = initPage(getDriver(), WikipediaHomePageBase.class);
        homePage.open();

        // Set the locale for this test
        R.CONFIG.put("locale", locale);

        // Reload L10N resources
        L10N.load();

        homePage.selectLanguage(language);

        SoftAssert softAssert = new SoftAssert();

        String actualLogoText = homePage.getLogoText();
        String expectedLogoText = L10N.getText("HomePage.logoText");
        softAssert.assertEquals(actualLogoText, expectedLogoText,
                "Logo text is not localized correctly for " + language);

        String actualMainPageText = homePage.getMainPageLinkText();
        String expectedMainPageText = L10N.getText("HomePage.mainPageLink");
        softAssert.assertEquals(actualMainPageText, expectedMainPageText,
                "Main page link text is not localized correctly for " + language);

        String actualRandomPageText = homePage.getRandomPageLinkText();
        String expectedRandomPageText = L10N.getText("HomePage.randomPageLink");
        softAssert.assertEquals(actualRandomPageText, expectedRandomPageText,
                "Random page link text is not localized correctly for " + language);

        softAssert.assertAll();
    }
}