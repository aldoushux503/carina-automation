package com.zebrunner.carina.automationexercise.web;


import com.zebrunner.carina.automationexercise.gui.pages.common.HomePageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.ProductsPageBase;
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

        additionalSetUp();
    }

    protected void additionalSetUp() {
        // Subclasses can override this to add their own setup logic
    }

    protected ProductsPageBase getVerifiedProductsPage() {
        ProductsPageBase productsPage = homePage.getProductsPage();
        Assert.assertTrue(productsPage.isPageOpened(), "Products page failed to load");
        return productsPage;
    }
}