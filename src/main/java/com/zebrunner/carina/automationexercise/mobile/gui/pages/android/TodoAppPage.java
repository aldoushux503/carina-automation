package com.zebrunner.carina.automationexercise.mobile.gui.pages.android;

import com.zebrunner.carina.automationexercise.mobile.gui.pages.common.TodoAppPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = TodoAppPageBase.class)
public class TodoAppPage extends TodoAppPageBase {
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

    @FindBy(xpath = "//android.widget.TextView[starts-with(@resource-id, 'taskText-')]")
    private List<ExtendedWebElement> taskTextElements;

    @FindBy(xpath = "//android.view.ViewGroup[starts-with(@resource-id, 'toggleTask-')]")
    private List<ExtendedWebElement> toggleTaskButtons;

    @FindBy(xpath = "//android.view.ViewGroup[starts-with(@resource-id, 'editTask-') and @content-desc='Edit']")
    private List<ExtendedWebElement> editTaskButtons;

    @FindBy(xpath = "//android.view.ViewGroup[starts-with(@resource-id, 'deleteTask-') and @content-desc='Delete']")
    private List<ExtendedWebElement> deleteTaskButtons;

    @FindBy(xpath = "//android.widget.Button[@resource-id='android:id/button1']")
    private ExtendedWebElement deleteConfirmButton;

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
        int taskIndex = findTaskIndex(taskText);
        if (taskIndex != -1) {
            toggleTaskButtons.get(taskIndex).click();
        }
    }

    @Override
    public void deleteTask(String taskText) {
        int taskIndex = findTaskIndex(taskText);
        if (taskIndex != -1) {
            deleteTaskButtons.get(taskIndex).click();
            deleteConfirmButton.click();
        }
    }

    @Override
    public void startEditTask(String taskText) {
        int taskIndex = findTaskIndex(taskText);
        if (taskIndex != -1) {
            editTaskButtons.get(taskIndex).click();
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
    public void selectFilterByLabel(String label) {
        if (label == null || label.isEmpty()) {
            throw new IllegalArgumentException("Filter label cannot be null or empty");
        }

        switch (label.toLowerCase()) {
            case "all":
                filterByAll();
                break;
            case "active":
                filterByActive();
                break;
            case "completed":
                filterByCompleted();
                break;
            default:
                throw new IllegalArgumentException("Unsupported filter label: " + label);
        }
    }

    @Override
    public void selectFilterByIndex(int index) {
        switch (index) {
            case 0:
                filterByAll();
                break;
            case 1:
                filterByActive();
                break;
            case 2:
                filterByCompleted();
                break;
            default:
                throw new IllegalArgumentException("Unsupported filter index: " + index);
        }
    }


    private void filterByAll() {
        filterAllButton.click();
    }

    private void filterByActive() {
        filterActiveButton.click();
    }

    private void filterByCompleted() {
        filterCompletedButton.click();
    }

    @Override
    public boolean isTaskDisplayed(String taskText) {
        return findTaskIndex(taskText) != -1;
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

    private int findTaskIndex(String taskText) {
        for (int i = 0; i < taskTextElements.size(); i++) {
            if (taskTextElements.get(i).getText().equals(taskText)) {
                return i;
            }
        }
        return -1;
    }
}