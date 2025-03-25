package com.zebrunner.carina.automationexercise.gui.pages.desktop;

import com.zebrunner.carina.automationexercise.gui.pages.common.ContactUsPageBase;
import com.zebrunner.carina.automationexercise.gui.pages.common.HomePageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = HomePageBase.class)
public class ContactUsPage extends ContactUsPageBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = "//h2[contains(@class, 'title text-center')]")
    private ExtendedWebElement getInTouchTitleText;

    @FindBy(xpath = "//input[contains(@placeholder, 'Name')]")
    private ExtendedWebElement nameInputField;

    @FindBy(xpath = "//input[contains(@placeholder, 'Email')]")
    private ExtendedWebElement emailInputField;

    @FindBy(xpath = "//input[contains(@placeholder, 'Subject')]")
    private ExtendedWebElement subjectInputField;

    @FindBy(xpath = "//textarea[contains(@placeholder, 'Your Message Here')]")
    private ExtendedWebElement messageTextareaField;

    @FindBy(xpath = "//input[contains(@name, 'upload_file')]")
    private ExtendedWebElement uploadFileInput;

    @FindBy(xpath = "//input[contains(@name, 'submit')]")
    private ExtendedWebElement submitButton;

    @FindBy(xpath = "//div[contains(text(),'Success! Your details have been submitted successfully.')]")
    private ExtendedWebElement successMessageText;

    public ContactUsPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(getInTouchTitleText);
    }

    @Override
    public void inputName(String name) {
        nameInputField.click();
        nameInputField.type(name);
    }

    @Override
    public void inputEmail(String email) {
        emailInputField.click();
        emailInputField.type(email);
    }

    @Override
    public void inputSubject(String subject) {
        subjectInputField.click();
        subjectInputField.type(subject);
    }

    @Override
    public void inputMessage(String message) {
        messageTextareaField.click();
        messageTextareaField.type(message);
    }

    @Override
    public void attacheFile(String filePath) {
        uploadFileInput.attachFile(filePath);
    }

    @Override
    public void clickSubmitButton() {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(submitButton.getElement()).click().perform();
        acceptAlert(5);
    }

    @Override
    public boolean isSuccessMessageVisible() {
        return successMessageText.isElementPresent();
    }

    /**
     * Helper method to accept an alert with better error handling
     *
     * @param timeoutInSeconds maximum time to wait for alert
     */
    private void acceptAlert(int timeoutInSeconds) {
        try {
            waitUntil(ExpectedConditions.alertIsPresent(), timeoutInSeconds);
            getDriver().switchTo().alert().accept();
        } catch (Exception e) {
            LOGGER.warn("No alert present or could not be accepted", e);
        }
    }
}