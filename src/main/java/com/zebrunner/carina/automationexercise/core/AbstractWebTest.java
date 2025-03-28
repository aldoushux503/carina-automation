package com.zebrunner.carina.automationexercise.core;


import com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise.HomePageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise.ProductsPageBase;
import com.zebrunner.carina.core.IAbstractTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

public abstract class AbstractWebTest implements IAbstractTest {

    protected HomePageBase homePage;

    @BeforeMethod(alwaysRun = true)
    public void baseSetUp() {
        homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page failed to load");
    }

}