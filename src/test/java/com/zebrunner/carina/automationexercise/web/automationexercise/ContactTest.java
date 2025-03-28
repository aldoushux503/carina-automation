package com.zebrunner.carina.automationexercise.web.automationexercise;

import com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise.ContactUsPageBase;
import com.zebrunner.carina.automationexercise.core.AbstractWebTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class ContactTest extends AbstractWebTest {

    @Test(dataProvider = "contactUsData")
    public void testContactFormSubmission(String testId, String name, String email,
                                          String subject, String message, String filePath) {
        ContactUsPageBase contactUsPage = homePage.getContactUsPage();
        Assert.assertTrue(contactUsPage.isPageOpened(), "Contact Us page failed to open");

        contactUsPage.inputName(name);
        contactUsPage.inputEmail(email);
        contactUsPage.inputSubject(subject);
        contactUsPage.inputMessage(message);
        contactUsPage.attacheFile(filePath);

        contactUsPage.clickSubmitButton();
        Assert.assertTrue(contactUsPage.isSuccessMessageVisible(),
                "Success message should be visible after form submission");
    }

    @DataProvider(name = "contactUsData")
    public Object[][] contactUsData() {
        return new Object[][] {
                {"TC01: Valid Contact Form", "Albert Einstein", "albert@example.com",
                        "Physics Question", "I have a question about relativity", "src/test/resources/files/icon.png"},
                {"TC02: Different Subject", "Marie Curie", "marie@example.com",
                        "Chemistry Query", "Question about radioactivity", "src/test/resources/files/icon.png"}
        };
    }
}