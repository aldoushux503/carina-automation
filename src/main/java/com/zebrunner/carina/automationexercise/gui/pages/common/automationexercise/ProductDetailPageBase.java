package com.zebrunner.carina.automationexercise.gui.pages.common.automationexercise;


import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;

public abstract class ProductDetailPageBase extends AbstractPage {

    public ProductDetailPageBase(WebDriver driver) {
        super(driver);
    }


    public abstract boolean isProductDetailPageOpened();


    public abstract String getProductName();


    public abstract String getProductPrice();


    public abstract void setQuantity(int quantity);


    public abstract int getQuantity();


    public abstract void clickAddToCart();


    public abstract CartPageBase clickViewCart();

    public abstract void clickContinueShopping();

    public abstract boolean isReviewSectionVisible();

    public abstract void inputReviewName(String name);

    public abstract void inputReviewEmail(String email);

    public abstract void inputReviewText(String review);

    public abstract void submitReview();

    public abstract boolean isReviewSuccessMessageVisible();
}