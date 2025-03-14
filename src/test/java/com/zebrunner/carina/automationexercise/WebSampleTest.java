package com.zebrunner.carina.automationexercise;

import com.zebrunner.carina.automationexercise.api.GetAllProductsMethod;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandles;


/**
 * This sample shows how create Web test.
 *
 */
public class WebSampleTest implements IAbstractTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());



    @Test()
    @MethodOwner(owner = "qpsdemo")
    public void testGetAllProducts() {
        GetAllProductsMethod getAllProductsMethod = new GetAllProductsMethod();
        getAllProductsMethod.callAPIExpectSuccess();
        getAllProductsMethod.validateResponse();
    }
}
