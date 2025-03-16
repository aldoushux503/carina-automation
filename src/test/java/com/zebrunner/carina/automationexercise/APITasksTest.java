package com.zebrunner.carina.automationexercise;

import com.zebrunner.carina.automationexercise.api.GetProductsListMethod;
import com.zebrunner.carina.automationexercise.api.PostCreateUser;
import com.zebrunner.carina.automationexercise.api.PostLoginMethods;
import com.zebrunner.carina.automationexercise.api.PostSearchProductMethod;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import org.testng.annotations.Test;

public class APITasksTest implements IAbstractTest {

    @Test()
    @MethodOwner(owner = "qpsdemo")
    public void testGetAllProducts() {
        GetProductsListMethod getProductsListMethod = new GetProductsListMethod();
        getProductsListMethod.callAPIExpectSuccess();
        getProductsListMethod.validateResponse();
    }

    @Test()
    @MethodOwner(owner = "qpsdemo")
    public void testPostSearchProducts() {
        PostSearchProductMethod postSearchProductMethod = new PostSearchProductMethod();
        postSearchProductMethod.callAPIExpectSuccess();
        postSearchProductMethod.validateResponseAgainstSchema("api/products/_post/rs.json");
    }

    @Test()
    @MethodOwner(owner = "qpsdemo")
    public void testValidUserLogin() {
        PostLoginMethods loginMethod = PostLoginMethods.createValidLoginMethod();
        loginMethod.callAPI();
        loginMethod.validateResponse();
    }

    @Test()
    @MethodOwner(owner = "qpsdemo")
    public void testLoginWithoutEmail() {
        PostLoginMethods loginMethod = PostLoginMethods.createLoginWithoutEmailMethod();
        loginMethod.callAPI();
        loginMethod.validateResponse();
    }

    @Test()
    @MethodOwner(owner = "qpsdemo")
    public void testLoginWithInvalidDetails() {
        PostLoginMethods loginMethod = PostLoginMethods.createInvalidLoginMethod();
        loginMethod.callAPI();
        loginMethod.validateResponse();
    }
}
