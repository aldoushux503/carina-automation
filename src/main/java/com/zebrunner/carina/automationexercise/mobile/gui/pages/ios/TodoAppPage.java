package com.zebrunner.carina.automationexercise.mobile.gui.pages.ios;

import com.zebrunner.carina.automationexercise.mobile.gui.pages.common.TodoAppPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import java.util.List;

@DeviceType(pageType = DeviceType.Type.IOS_PHONE, parentClass = TodoAppPageBase.class)
public class TodoAppPage extends TodoAppPageBase {

    private static final String TOGGLE_TASK_XPATH = "//XCUIElementTypeButton[@name='toggleTask-%s']";
    private static final String EDIT_TASK_XPATH = "//XCUIElementTypeButton[@name='editTask-%s' and @label='Edit']";
    private static final String DELETE_TASK_XPATH = "//XCUIElementTypeButton[@name='deleteTask-%s' and @label='Delete']";
    private static final String TASK_TEXT_BY_TEXT_XPATH = "//XCUIElementTypeStaticText[@label='%s']";
    private static final String ALL_TASKS_TEXT_XPATH = "//XCUIElementTypeStaticText[starts-with(@name, 'taskText-')]";
    private static final String DELETE_CONFIRM_BUTTON_XPATH = "//XCUIElementTypeButton[@label='Delete']";

    @FindBy(xpath = "//XCUIElementTypeTextField[@name='taskInput']")
    private ExtendedWebElement taskInput;

    @FindBy(xpath = "//XCUIElementTypeButton[@name='addTask' and @label='Add']")
    private ExtendedWebElement addTaskButton;

    @FindBy(xpath = "//XCUIElementTypeScrollView[@name='taskList']")
    private ExtendedWebElement taskList;

    @FindBy(xpath = "//XCUIElementTypeButton[@name='filterAll' and @label='All']")
    private ExtendedWebElement filterAllButton;

    @FindBy(xpath = "//XCUIElementTypeButton[@name='filterActive' and @label='Active']")
    private ExtendedWebElement filterActiveButton;

    @FindBy(xpath = "//XCUIElementTypeButton[@name='filterCompleted' and @label='Completed']")
    private ExtendedWebElement filterCompletedButton;

    @FindBy(xpath = "//XCUIElementTypeStaticText[@name='taskCount']")
    private ExtendedWebElement taskCountText;

    @FindBy(xpath = "//XCUIElementTypeStaticText[@label='Save']")
    private ExtendedWebElement saveTaskButton;

    @FindBy(xpath = "//XCUIElementTypeStaticText[@label='Cancel']")
    private ExtendedWebElement cancelEditButton;

    @FindBy(xpath = "//XCUIElementTypeStaticText[contains(@label, 'TODO APP')]")
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
                String resourceId = element.getAttribute("name");
                if (resourceId != null) {
                    return resourceId.replace("taskText-", "");
                }
            }
        }

        return null;
    }
}