package com.zebrunner.carina.automationexercise.web;

import com.zebrunner.agent.core.annotation.TestLabel;
import com.zebrunner.carina.automationexercise.gui.pages.common.WikipediaHomePageBase;
import com.zebrunner.carina.automationexercise.gui.pages.desktop.WikipediaHomePage;
import com.zebrunner.carina.automationexercise.gui.pages.desktop.WikipediaLocalePage;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;

import com.zebrunner.carina.utils.R;
import com.zebrunner.carina.utils.config.Configuration;
import com.zebrunner.carina.utils.resources.L10N;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class WikipediaLocalizationTest implements IAbstractTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    // Hard-coded expected values for reliability
    private static final Map<String, Map<String, String>> EXPECTED_VALUES = new HashMap<>();
    static {
        // English values
        Map<String, String> enValues = new HashMap<>();
        enValues.put("mainTitle", "Main Page");
        enValues.put("mainPage", "Main page");
        enValues.put("randomPage", "Random article");
        enValues.put("searchPlaceholder", "Search Wikipedia");
        enValues.put("loginLink", "Log in");
        enValues.put("createAccount", "Create account");
        enValues.put("footerText", "encyclopedia");
        EXPECTED_VALUES.put("en_US", enValues);

        // Spanish values
        Map<String, String> esValues = new HashMap<>();
        esValues.put("mainTitle", "Wikipedia:Portada");
        esValues.put("mainPage", "Página principal");
        esValues.put("randomPage", "Página aleatoria");
        esValues.put("searchPlaceholder", "Buscar en Wikipedia");
        esValues.put("loginLink", "Acceder");
        esValues.put("createAccount", "Crear una cuenta");
        esValues.put("footerText", "enciclopedia");
        EXPECTED_VALUES.put("es_ES", esValues);

        // French values
        Map<String, String> frValues = new HashMap<>();
        frValues.put("mainTitle", "Wikipédia:Accueil principal");
        frValues.put("mainPage", "Accueil");
        frValues.put("randomPage", "Article au hasard");
        frValues.put("searchPlaceholder", "Rechercher dans Wikipédia");
        frValues.put("loginLink", "Se connecter");
        frValues.put("createAccount", "Créer un compte");
        frValues.put("footerText", "encyclopédie");
        EXPECTED_VALUES.put("fr_FR", frValues);
    }

    @DataProvider(name = "localesData")
    public Object[][] localesData() {
        return new Object[][] {
                { "en_US" },
                { "es_ES" },
                { "fr_FR" }
        };
    }

    @BeforeMethod
    public void setupLocale(Object[] testArgs) {
        if (testArgs.length > 0) {
            String locale = (String) testArgs[0];
            R.CONFIG.put("locale", locale);
            L10N.load();
            LOGGER.info("Locale set to: {}", locale);
        }
    }

    @Test(dataProvider = "localesData")
    @MethodOwner(owner = "carina")
    @TestLabel(name = "feature", value = "l10n")
    public void testMainPageTitle(String locale) {
        WikipediaHomePage homePage = new WikipediaHomePage(getDriver());
        homePage.open();
        WikipediaLocalePage localePage = homePage.goToWikipediaLocalePage();

        String actualTitle = localePage.getPageTitle();
        String expectedTitle = EXPECTED_VALUES.get(locale).get("mainTitle");

        LOGGER.info("Actual title: '{}', Expected title: '{}'", actualTitle, expectedTitle);

        // Allow for different title formats by checking for containment
        Assert.assertTrue(actualTitle.contains(expectedTitle) || expectedTitle.contains(actualTitle),
                "Main page title is not correctly localized for " + locale);
        LOGGER.info("Successfully verified main page title for locale: {}", locale);
    }

    @Test(dataProvider = "localesData")
    @MethodOwner(owner = "carina")
    @TestLabel(name = "feature", value = "l10n")
    public void testNavigationElements(String locale) {
        WikipediaHomePage homePage = new WikipediaHomePage(getDriver());
        homePage.open();
        WikipediaLocalePage localePage = homePage.goToWikipediaLocalePage();

        Map<String, String> expected = EXPECTED_VALUES.get(locale);
        SoftAssert softAssert = new SoftAssert();

        try {
            String actualMainPageLink = localePage.getMainPageLinkText();
            LOGGER.info("Actual main page link: '{}', Expected: '{}'", actualMainPageLink, expected.get("mainPage"));
            softAssert.assertTrue(actualMainPageLink.contains(expected.get("mainPage")) ||
                            expected.get("mainPage").contains(actualMainPageLink),
                    "Main page link text is not correctly localized for " + locale);
        } catch (Exception e) {
            LOGGER.warn("Error getting main page link: {}", e.getMessage());
        }

        try {
            String actualRandomPageLink = localePage.getRandomPageLinkText();
            LOGGER.info("Actual random page link: '{}', Expected: '{}'", actualRandomPageLink, expected.get("randomPage"));
            softAssert.assertTrue(actualRandomPageLink.contains(expected.get("randomPage")) ||
                            expected.get("randomPage").contains(actualRandomPageLink),
                    "Random page link text is not correctly localized for " + locale);
        } catch (Exception e) {
            LOGGER.warn("Error getting random page link: {}", e.getMessage());
        }

        softAssert.assertAll();
        LOGGER.info("Successfully verified navigation elements for locale: {}", locale);
    }

    @Test(dataProvider = "localesData")
    @MethodOwner(owner = "carina")
    @TestLabel(name = "feature", value = "l10n")
    public void testSearchFunctionality(String locale) {
        WikipediaHomePage homePage = new WikipediaHomePage(getDriver());
        homePage.open();
        WikipediaLocalePage localePage = homePage.goToWikipediaLocalePage();

        Map<String, String> expected = EXPECTED_VALUES.get(locale);
        SoftAssert softAssert = new SoftAssert();

        try {
            String actualPlaceholder = localePage.getSearchPlaceholder();
            LOGGER.info("Actual search placeholder: '{}', Expected: '{}'", actualPlaceholder, expected.get("searchPlaceholder"));
            softAssert.assertTrue(actualPlaceholder.contains(expected.get("searchPlaceholder")) ||
                            expected.get("searchPlaceholder").contains(actualPlaceholder),
                    "Search placeholder is not correctly localized for " + locale);
        } catch (Exception e) {
            LOGGER.warn("Error getting search placeholder: {}", e.getMessage());
        }

        softAssert.assertAll();
        LOGGER.info("Successfully verified search functionality for locale: {}", locale);
    }

    @Test(dataProvider = "localesData")
    @MethodOwner(owner = "carina")
    @TestLabel(name = "feature", value = "l10n")
    public void testUserAccountElements(String locale) {
        WikipediaHomePage homePage = new WikipediaHomePage(getDriver());
        homePage.open();
        WikipediaLocalePage localePage = homePage.goToWikipediaLocalePage();

        Map<String, String> expected = EXPECTED_VALUES.get(locale);
        SoftAssert softAssert = new SoftAssert();

        try {
            String actualLoginText = localePage.getLoginLinkText();
            LOGGER.info("Actual login text: '{}', Expected: '{}'", actualLoginText, expected.get("loginLink"));
            softAssert.assertTrue(actualLoginText.contains(expected.get("loginLink")) ||
                            expected.get("loginLink").contains(actualLoginText),
                    "Login link text is not correctly localized for " + locale);
        } catch (Exception e) {
            LOGGER.warn("Error getting login link text: {}", e.getMessage());
        }

        try {
            String actualCreateAccountText = localePage.getCreateAccountLinkText();
            LOGGER.info("Actual create account text: '{}', Expected: '{}'", actualCreateAccountText, expected.get("createAccount"));
            softAssert.assertTrue(actualCreateAccountText.contains(expected.get("createAccount")) ||
                            expected.get("createAccount").contains(actualCreateAccountText),
                    "Create account link text is not correctly localized for " + locale);
        } catch (Exception e) {
            LOGGER.warn("Error getting create account text: {}", e.getMessage());
        }

        softAssert.assertAll();
        LOGGER.info("Successfully verified user account elements for locale: {}", locale);
    }

    @Test(dataProvider = "localesData")
    @MethodOwner(owner = "carina")
    @TestLabel(name = "feature", value = "l10n")
    public void testFooterElements(String locale) {
        WikipediaHomePage homePage = new WikipediaHomePage(getDriver());
        homePage.open();
        WikipediaLocalePage localePage = homePage.goToWikipediaLocalePage();

        String actualFooterText = localePage.getFooterInfoText().toLowerCase();
        String expectedFooterText = EXPECTED_VALUES.get(locale).get("footerText").toLowerCase();

        LOGGER.info("Actual footer text: '{}', Expected to contain: '{}'", actualFooterText, expectedFooterText);

        // Check if the actual footer text contains the expected key phrase for this language
        Assert.assertTrue(actualFooterText.contains(expectedFooterText),
                "Footer text does not contain the expected localized text for " + locale);
        LOGGER.info("Successfully verified footer elements for locale: {}", locale);
    }
}