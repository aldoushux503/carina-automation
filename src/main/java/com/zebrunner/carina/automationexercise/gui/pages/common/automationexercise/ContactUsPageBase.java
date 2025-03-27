package com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.io.File;

public abstract class ContactUsPageBase extends AbstractPage {
    public ContactUsPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract void inputName(String name);
    public abstract void inputEmail(String email);
    public abstract void inputSubject(String subject);
    public abstract void inputMessage(String message);
    public abstract void attacheFile(String filePath);
    public abstract void clickSubmitButton();

    public abstract boolean isSuccessMessageVisible();
}
