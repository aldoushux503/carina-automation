package com.zebrunner.carina.automationexercise.api;

import com.zebrunner.carina.automationexercise.api.login.PostLoginMethods;
import com.zebrunner.carina.automationexercise.api.product.GetProductsListMethod;
import com.zebrunner.carina.automationexercise.api.product.PostSearchProductMethod;
import com.zebrunner.carina.core.IAbstractTest;
import org.testng.annotations.Test;

public class APIProductTest implements IAbstractTest {

    @Test()
    public void testGetAllProducts() {
        GetProductsListMethod getProductsListMethod = new GetProductsListMethod();
        getProductsListMethod.callAPIExpectSuccess();
        getProductsListMethod.validateResponse();
    }

    @Test()
    public void testPostSearchProducts() {
        PostSearchProductMethod postSearchProductMethod = new PostSearchProductMethod();
        postSearchProductMethod.callAPIExpectSuccess();
        postSearchProductMethod.validateResponseAgainstSchema("api/products/_post/rs.json");
    }

    @Test()
    public void testValidUserLogin() {
        PostLoginMethods loginMethod = PostLoginMethods.createValidLoginMethod();
        loginMethod.callAPI();
        loginMethod.validateResponse();
    }

    @Test()
    public void testLoginWithoutEmail() {
        PostLoginMethods loginMethod = PostLoginMethods.createLoginWithoutEmailMethod();
        loginMethod.callAPI();
        loginMethod.validateResponse();
    }

    @Test()
    public void testLoginWithInvalidDetails() {
        PostLoginMethods loginMethod = PostLoginMethods.createInvalidLoginMethod();
        loginMethod.callAPI();
        loginMethod.validateResponse();
    }
}
