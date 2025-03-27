package com.zebrunner.carina.automationexercise.mobile;

import com.zebrunner.carina.automationexercise.mobile.gui.pages.common.HomePageBase;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.utils.mobile.IMobileUtils;
import org.testng.annotations.Test;

public class AndroidSampleTest implements IAbstractTest, IMobileUtils {


    @Test
    public void testClickActiveButton() {
        HomePageBase homePage = initPage(getDriver(), HomePageBase.class);
        homePage.clickActiveButton();
    }
}
