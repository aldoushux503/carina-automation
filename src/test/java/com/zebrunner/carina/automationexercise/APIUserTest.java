package com.zebrunner.carina.automationexercise;

import com.zebrunner.agent.core.annotation.Priority;
import com.zebrunner.carina.automationexercise.api.DeleteUserMethod;
import com.zebrunner.carina.automationexercise.api.GetUserMethod;
import com.zebrunner.carina.automationexercise.api.PostCreateUser;
import com.zebrunner.carina.automationexercise.api.PutUpdateUserMethod;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class APIUserTest implements IAbstractTest {

    @Test(groups = "create")
    public void testCreateUser() {
        PostCreateUser createUser = new PostCreateUser();
        createUser.callAPIExpectSuccess();
        createUser.validateResponse();
    }
    @Test(groups = "create", dependsOnMethods = "testCreateUser")
    public void testRepeatCreateUser() {
        PostCreateUser createSameUser = new PostCreateUser();
        createSameUser.callAPIExpectSuccess();
        createSameUser.setResponseTemplate("api/users/_post/repeat_rs.json");
        createSameUser.validateResponse();
    }

    @Test(groups = "get", dependsOnGroups = {"create"})
    public void testGetUser() {
        GetUserMethod getUserMethod = new GetUserMethod();
        getUserMethod.callAPIExpectSuccess();
        getUserMethod.validateResponse();
    }

    @Test(groups = "delete", dependsOnGroups = {"create", "update", "get"},  alwaysRun = true)
    public void testDeleteUser() {
        DeleteUserMethod deleteUser = new DeleteUserMethod();
        deleteUser.callAPIExpectSuccess();
        deleteUser.validateResponse();
    }

    @Test(groups = "update", dependsOnGroups = {"create", "get"})
    public void testUpdateUser() {
        PutUpdateUserMethod updateUser = new PutUpdateUserMethod();
        updateUser.callAPIExpectSuccess();
        updateUser.validateResponse();
    }
}
