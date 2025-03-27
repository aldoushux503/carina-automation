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

import com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise.ContactUsPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise.HomePageBase;
import com.zebrunner.carina.automationexercise.gui.pages.desktop.automationexercise.CartPage;
import com.zebrunner.carina.automationexercise.gui.pages.desktop.automationexercise.ProductsPage;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

public abstract class TopMenuBase extends AbstractUIObject {

    public TopMenuBase(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }


    public abstract CartPage openCartPage();
    public abstract HomePageBase openHomePage();
    public abstract ContactUsPageBase openContactUsPage();
    public abstract ProductsPage openProductsPage();
}
