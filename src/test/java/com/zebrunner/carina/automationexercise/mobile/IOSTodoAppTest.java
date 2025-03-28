package com.zebrunner.carina.automationexercise.mobile;

import com.zebrunner.carina.automationexercise.mobile.gui.pages.common.TodoAppPageBase;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.utils.mobile.IMobileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class IOSTodoAppTest implements IAbstractTest, IMobileUtils {

    private static final int RANDOM_STRING_LENGTH = 5;
    private static final int LONG_TEXT_LENGTH = 100;

    private TodoAppPageBase todoPage;

    @BeforeMethod
    public void setUp() {
        todoPage = initPage(getDriver(), TodoAppPageBase.class);
    }

    @Test(description = "Test with very long task text on iOS")
    public void testLongTaskText() {
        String longTaskText = RandomStringUtils.randomAlphanumeric(LONG_TEXT_LENGTH);
        todoPage.addTask(longTaskText);

        Assert.assertTrue(todoPage.isTaskDisplayed(longTaskText),
                "Long task text should be displayed correctly");
    }

    @Test(description = "Test with special characters in task text on iOS")
    public void testSpecialCharactersInTaskText() {
        String taskWithSpecialChars = "!@#$%^&*()_+{}|:<>?~";
        todoPage.addTask(taskWithSpecialChars);

        Assert.assertTrue(todoPage.isTaskDisplayed(taskWithSpecialChars),
                "Task with special characters should be displayed correctly");
    }


    @Test(description = "Test empty task text on iOS")
    public void testEmptyTaskText() {
        String emptyText = "";
        todoPage.addTask(emptyText);

        // This test assumes the app has validation and won't add empty tasks
        todoPage.filterByAll();
        Assert.assertFalse(todoPage.isTaskDisplayed(emptyText),
                "Empty task should not be added");
    }

    @Test(description = "Test duplicate task text on iOS")
    public void testDuplicateTaskText() {
        String duplicateText = RandomStringUtils.randomAlphanumeric(RANDOM_STRING_LENGTH);

        // Add the same task twice
        todoPage.addTask(duplicateText);
        todoPage.addTask(duplicateText);

        todoPage.filterByAll();

        // Verify at least one instance of the task exists
        Assert.assertTrue(todoPage.isTaskDisplayed(duplicateText),
                "At least one instance of duplicate task should be displayed");
    }

}