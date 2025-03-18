/*******************************************************************************
 * Copyright 2020-2023 Zebrunner Inc (https://www.zebrunner.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.zebrunner.carina.automationexercise.gui.components.topMenu;

import com.zebrunner.carina.automationexercise.gui.pages.desktop.ContactUsPage;
import com.zebrunner.carina.automationexercise.gui.pages.desktop.ProductsPage;
import com.zebrunner.carina.demo.gui.pages.desktop.CompareModelsPage;
import com.zebrunner.carina.demo.gui.pages.desktop.HomePage;
import com.zebrunner.carina.demo.gui.pages.desktop.NewsPage;
import com.zebrunner.carina.demo.mobile.gui.pages.common.ContactUsPageBase;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TopMenu extends TopMenuBase {

    @FindBy(xpath = "//a[contains(text(),'Home')]")
    private ExtendedWebElement homeLink;


    @FindBy(xpath = "//a[contains(text(),'Products')]")
    private ExtendedWebElement products;
    

    @FindBy(xpath = "//a[contains(text(),'Cart')]")
    private ExtendedWebElement cartLink;

    @FindBy(xpath = "//a[contains(text(),'Signup / Login')]")
    private ExtendedWebElement login;

    @FindBy(xpath = "//a[contains(@href, '/contact_us')]")

    private ExtendedWebElement contactUs;


    public TopMenu(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    @Override
    public HomePage openHomePage() {
        homeLink.click();
        return new HomePage(driver);
    }

    @Override
    public ContactUsPage openContactUsPage() {
        contactUs.click();
        return new ContactUsPage(driver);
    }

    @Override
    public ProductsPage openProductsPage() {
        products.click();
        return new ProductsPage(driver);
    }
}
