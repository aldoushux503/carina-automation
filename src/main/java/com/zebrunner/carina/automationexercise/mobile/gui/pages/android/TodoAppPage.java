package com.zebrunner.carina.automationexercise.mobile.gui.pages.android;

import com.zebrunner.carina.automationexercise.mobile.gui.pages.common.TodoAppPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import java.util.List;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = TodoAppPageBase.class)
public class TodoAppPage extends TodoAppPageBase {

    private static final String TOGGLE_TASK_XPATH = "//android.view.ViewGroup[@resource-id='toggleTask-%s']";
    private static final String EDIT_TASK_XPATH = "//android.view.ViewGroup[@resource-id='editTask-%s' and @content-desc='Edit']";
    private static final String DELETE_TASK_XPATH = "//android.view.ViewGroup[@resource-id='deleteTask-%s' and @content-desc='Delete']";
    private static final String TASK_TEXT_XPATH = "//android.widget.TextView[@resource-id='taskText-%s']";
    private static final String TASK_TEXT_BY_TEXT_XPATH = "//android.widget.TextView[@text='%s']";
    private static final String ALL_TASKS_TEXT_XPATH = "//android.widget.TextView[starts-with(@resource-id, 'taskText-')]";
    private static final String DELETE_CONFIRM_BUTTON_XPATH = "//android.widget.Button[@resource-id='android:id/button1']";

    @FindBy(xpath = "//android.widget.EditText[@resource-id='taskInput']")
    private ExtendedWebElement taskInput;

    @FindBy(xpath = "//android.view.ViewGroup[@resource-id='addTask' and @content-desc='Add']")
    private ExtendedWebElement addTaskButton;

    @FindBy(xpath = "//android.widget.ScrollView[@resource-id='taskList']")
    private ExtendedWebElement taskList;

    @FindBy(xpath = "//android.view.ViewGroup[@resource-id='filterAll' and @content-desc='All']")
    private ExtendedWebElement filterAllButton;

    @FindBy(xpath = "//android.view.ViewGroup[@resource-id='filterActive' and @content-desc='Active']")
    private ExtendedWebElement filterActiveButton;

    @FindBy(xpath = "//android.view.ViewGroup[@resource-id='filterCompleted' and @content-desc='Completed']")
    private ExtendedWebElement filterCompletedButton;

    @FindBy(xpath = "//android.widget.TextView[@resource-id='taskCount']")
    private ExtendedWebElement taskCountText;

    @FindBy(xpath = "//android.widget.TextView[@text='Save']")
    private ExtendedWebElement saveTaskButton;

    @FindBy(xpath = "//android.widget.TextView[@text='Cancel']")
    private ExtendedWebElement cancelEditButton;

    @FindBy(xpath = "//android.widget.TextView[contains(@text, 'TODO APP')]")
    private ExtendedWebElement headerText;

    public TodoAppPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(headerText);
    }

    @Override
    public boolean isAppLaunched() {
        return waitUntil(d -> taskInput.isElementPresent() && addTaskButton.isElementPresent(), 3);
    }

    @Override
    public void addTask(String taskText) {
        taskInput.type(taskText);
        addTaskButton.click();
    }

    @Override
    public void toggleTaskCompletion(String taskText) {
        String taskId = findTaskIdByText(taskText);
        if (taskId != null) {
            findExtendedWebElement(By.xpath(String.format(TOGGLE_TASK_XPATH, taskId))).click();
        }
    }

    @Override
    public void deleteTask(String taskText) {
        String taskId = findTaskIdByText(taskText);
        if (taskId != null) {
            findExtendedWebElement(By.xpath(String.format(DELETE_TASK_XPATH, taskId))).click();
            findExtendedWebElement(By.xpath(DELETE_CONFIRM_BUTTON_XPATH)).click();
        }
    }

    @Override
    public void startEditTask(String taskText) {
        String taskId = findTaskIdByText(taskText);
        if (taskId != null) {
            findExtendedWebElement(By.xpath(String.format(EDIT_TASK_XPATH, taskId))).click();
        }
    }

    @Override
    public void typeInTaskInput(String text) {
        taskInput.type(text);
    }

    @Override
    public void editTask(String oldTaskText, String newTaskText) {
        startEditTask(oldTaskText);
        typeInTaskInput(newTaskText);
        saveTaskButton.click();
    }

    @Override
    public void cancelEditTask() {
        cancelEditButton.click();
    }

    @Override
    public void filterByAll() {
        filterAllButton.click();
    }

    @Override
    public void filterByActive() {
        filterActiveButton.click();
    }

    @Override
    public void filterByCompleted() {
        filterCompletedButton.click();
    }

    @Override
    public boolean isTaskDisplayed(String taskText) {
        ExtendedWebElement element = findExtendedWebElement(By.xpath(String.format(TASK_TEXT_BY_TEXT_XPATH, taskText)));
        if(element == null) {
            return false;
        }

        return element.isElementPresent();
    }


    @Override
    public int getRemainingTaskCount() {
        String countText = taskCountText.getText();
        return Integer.parseInt(countText.split(" ")[0]);
    }

    @Override
    public String getRemainingTaskCountText() {
        return taskCountText.getText();
    }

    private String findTaskIdByText(String taskText) {
        List<ExtendedWebElement> taskTextElements = findExtendedWebElements(By.xpath(ALL_TASKS_TEXT_XPATH));

        for (ExtendedWebElement element : taskTextElements) {
            if (element.getText().equals(taskText)) {
                String resourceId = element.getAttribute("resource-id");
                if (resourceId != null) {
                    return resourceId.replace("taskText-", "");
                }
            }
        }

        return null;
    }
}