package com.zebrunner.carina.automationexercise.mobile.gui.pages.ios;

import com.zebrunner.carina.automationexercise.mobile.gui.pages.common.TodoAppPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.locator.ExtendedFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import java.util.List;

@DeviceType(pageType = DeviceType.Type.IOS_PHONE, parentClass = TodoAppPageBase.class)
public class TodoAppPage extends TodoAppPageBase {

    @ExtendedFindBy(iosClassChain = "//XCUIElementTypeTextField[@name='taskInput']")
    private ExtendedWebElement taskInput;

    @ExtendedFindBy(iosClassChain = "//XCUIElementTypeButton[@name='addTask' and @label='Add']")
    private ExtendedWebElement addTaskButton;

    @ExtendedFindBy(iosClassChain = "//XCUIElementTypeScrollView[@name='taskList']")
    private ExtendedWebElement taskList;

    @ExtendedFindBy(iosClassChain = "//XCUIElementTypeButton[@name='filterAll' and @label='All']")
    private ExtendedWebElement filterAllButton;

    @ExtendedFindBy(iosClassChain = "//XCUIElementTypeButton[@name='filterActive' and @label='Active']")
    private ExtendedWebElement filterActiveButton;

    @ExtendedFindBy(iosClassChain = "//XCUIElementTypeButton[@name='filterCompleted' and @label='Completed']")
    private ExtendedWebElement filterCompletedButton;

    @ExtendedFindBy(iosClassChain = "//XCUIElementTypeStaticText[@name='taskCount']")
    private ExtendedWebElement taskCountText;

    @ExtendedFindBy(iosClassChain = "//XCUIElementTypeStaticText[@label='Save']")
    private ExtendedWebElement saveTaskButton;

    @ExtendedFindBy(iosClassChain = "//XCUIElementTypeStaticText[@label='Cancel']")
    private ExtendedWebElement cancelEditButton;

    @ExtendedFindBy(iosClassChain = "//XCUIElementTypeStaticText[contains(@label, 'TODO APP')]")
    private ExtendedWebElement headerText;

    @ExtendedFindBy(iosClassChain = "//XCUIElementTypeStaticText[starts-with(@name, 'taskText-')]")
    private List<ExtendedWebElement> taskTextElements;

    @ExtendedFindBy(iosClassChain = "//XCUIElementTypeButton[starts-with(@name, 'toggleTask-')]")
    private List<ExtendedWebElement> toggleTaskButtons;

    @ExtendedFindBy(iosClassChain = "//XCUIElementTypeButton[starts-with(@name, 'editTask-') and @label='Edit']")
    private List<ExtendedWebElement> editTaskButtons;

    @ExtendedFindBy(iosClassChain = "//XCUIElementTypeButton[starts-with(@name, 'deleteTask-') and @label='Delete']")
    private List<ExtendedWebElement> deleteTaskButtons;

    @ExtendedFindBy(iosClassChain = "//XCUIElementTypeButton[@label='Delete']")
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