package com.zebrunner.carina.automationexercise.gui.pages.desktop;

import com.zebrunner.carina.automationexercise.gui.pages.common.ContactUsPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.HomePageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.io.File;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = HomePageBase.class)
public class ContactUsPage extends ContactUsPageBase {

    @FindBy(xpath = "//h2[contains(@class, 'title text-center')]")
    private ExtendedWebElement getInTouchTitle;

    @FindBy(xpath = "//input[contains(@placeholder, 'Name')]")
    private ExtendedWebElement namePlaceHolder;

    @FindBy(xpath = "//input[contains(@placeholder, 'Email')]")
    private ExtendedWebElement emailPlaceHolder;

    @FindBy(xpath = "//input[contains(@placeholder, 'Subject')]")
    private ExtendedWebElement subjectPlaceHolder;

    @FindBy(xpath = "//textarea[contains(@placeholder, 'Your Message Here')]")
    private ExtendedWebElement messagePlaceHolder;

    @FindBy(xpath = "//input[contains(@name, 'upload_file')]")
    private ExtendedWebElement uploadFileInput;

    @FindBy(xpath = "//input[contains(@name, 'submit')]")
    private ExtendedWebElement submitButton;
    public ContactUsPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(getInTouchTitle);
    }

    @Override
    public void inputName(String name) {
        namePlaceHolder.click();
        namePlaceHolder.type(name);
    }
    @Override
    public void inputEmail(String email) {
        emailPlaceHolder.click();
        emailPlaceHolder.type(email);
    }

    @Override
    public void inputSubject(String subject) {
        subjectPlaceHolder.click();
        subjectPlaceHolder.type(subject);
    }

    @Override
    public void inputMessage(String message) {
        messagePlaceHolder.click();
        messagePlaceHolder.type(message);
    }

    @Override
    public void attacheFile(String filePath) {
        uploadFileInput.attachFile(filePath);
    }

    @Override
    public void clickSubmitButton() {
        submitButton.click();
    }
}
