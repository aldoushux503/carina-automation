package com.zebrunner.carina.automationexercise.api;

import com.zebrunner.carina.automationexercise.api.user.DeleteUserMethod;
import com.zebrunner.carina.automationexercise.api.user.GetUserMethod;
import com.zebrunner.carina.automationexercise.api.user.PostCreateUser;
import com.zebrunner.carina.automationexercise.api.user.PutUpdateUserMethod;
import com.zebrunner.carina.core.IAbstractTest;
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
