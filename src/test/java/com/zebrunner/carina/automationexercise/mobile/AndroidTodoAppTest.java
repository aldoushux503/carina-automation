package com.zebrunner.carina.automationexercise.mobile;

import com.zebrunner.carina.automationexercise.mobile.gui.pages.common.TodoAppPageBase;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.utils.mobile.IMobileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AndroidTodoAppTest implements IAbstractTest, IMobileUtils {

    private static final int RANDOM_STRING_LENGTH = 5;

    private TodoAppPageBase todoPage;

    @BeforeMethod
    public void setUp() {
        todoPage = initPage(getDriver(), TodoAppPageBase.class);
    }

    private String generateTaskName(String prefix) {
        return prefix + RandomStringUtils.randomAlphanumeric(RANDOM_STRING_LENGTH);
    }

    private String createTask(String prefix) {
        String taskText = generateTaskName(prefix);
        todoPage.addTask(taskText);
        Assert.assertTrue(todoPage.isTaskDisplayed(taskText), "Task should be added and visible");
        return taskText;
    }

    @Test(description = "Verify app launches successfully")
    public void testAppLaunch() {
        Assert.assertTrue(todoPage.isAppLaunched(), "Todo app should launch successfully");
    }

    @Test(description = "Verify adding a new task")
    public void testAddTask() {
        String taskText = generateTaskName("Add_");
        todoPage.addTask(taskText);
        Assert.assertTrue(todoPage.isTaskDisplayed(taskText), "Added task should be displayed");
    }

    @Test(description = "Verify marking a task as completed")
    public void testMarkTaskAsCompleted() {
        String taskText = createTask("Complete_");

        todoPage.toggleTaskCompletion(taskText);
        todoPage.selectFilterByLabel("Completed");
        Assert.assertTrue(todoPage.isTaskDisplayed(taskText), "Task should be visible in Completed filter");
    }

    @Test(description = "Verify deleting a task")
    public void testDeleteTask() {
        String taskText = createTask("Delete_");

        todoPage.deleteTask(taskText);
        Assert.assertFalse(todoPage.isTaskDisplayed(taskText), "Task should not be displayed after deletion");
    }

    @Test(description = "Verify editing a task")
    public void testEditTask() {
        String oldTaskText = createTask("Old_");
        String newTaskText = generateTaskName("New_");

        todoPage.editTask(oldTaskText, newTaskText);

        Assert.assertFalse(todoPage.isTaskDisplayed(oldTaskText), "Old task should not be displayed after editing");
        Assert.assertTrue(todoPage.isTaskDisplayed(newTaskText), "New task should be displayed after editing");
    }

    @DataProvider(name = "filterTestData")
    public Object[][] getFilterTestData() {
        return new Object[][]{
                {"All", true, true},
                {"Active", true, false},
                {"Completed", false, true}
        };
    }

    @Test(dataProvider = "filterTestData", description = "Verify filter functionality")
    public void testFilters(String filterType, boolean shouldActiveBeVisible, boolean shouldCompletedBeVisible) {
        String activeTask = createTask("Active_");
        String completedTask = createTask("Completed_");
        todoPage.toggleTaskCompletion(completedTask);

        todoPage.selectFilterByLabel(filterType);

        Assert.assertEquals(todoPage.isTaskDisplayed(activeTask), shouldActiveBeVisible,
                "Active task visibility incorrect with '" + filterType + "' filter");
        Assert.assertEquals(todoPage.isTaskDisplayed(completedTask), shouldCompletedBeVisible,
                "Completed task visibility incorrect with '" + filterType + "' filter");
    }

    @Test(description = "Verify cancelling task edit")
    public void testCancelTaskEdit() {
        String taskText = createTask("Cancel_");
        String newText = "Unsaved_" + RandomStringUtils.randomAlphanumeric(RANDOM_STRING_LENGTH);

        todoPage.startEditTask(taskText);
        todoPage.typeInTaskInput(newText);
        todoPage.cancelEditTask();

        Assert.assertTrue(todoPage.isTaskDisplayed(taskText), "Original task should still be displayed after cancelling edit");
        Assert.assertFalse(todoPage.isTaskDisplayed(newText), "New text should not appear after cancelling edit");
    }

    @Test(description = "Verify task count display")
    public void testTaskCountDisplay() {
        String prefix = "Count_" + RandomStringUtils.randomAlphanumeric(RANDOM_STRING_LENGTH);

        for (int i = 1; i <= 3; i++) {
            todoPage.addTask(prefix + "_Task_" + i);
        }

        todoPage.selectFilterByLabel("Active");
        int initialCount = todoPage.getRemainingTaskCount();

        todoPage.toggleTaskCompletion(prefix + "_Task_1");

        int newCount = todoPage.getRemainingTaskCount();
        Assert.assertEquals(newCount, initialCount - 1, "Task count should decrease by 1 after completing a task");
    }
}