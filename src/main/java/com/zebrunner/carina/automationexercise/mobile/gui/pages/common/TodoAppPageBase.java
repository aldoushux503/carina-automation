package com.zebrunner.carina.automationexercise.mobile.gui.pages.common;

import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;

public abstract class TodoAppPageBase extends AbstractPage {

    public TodoAppPageBase(WebDriver driver) {
        super(driver);
    }

    // Launch verification
    public abstract boolean isAppLaunched();

    // Task operations
    public abstract void addTask(String taskText);

    public abstract void toggleTaskCompletion(String taskText);

    public abstract void deleteTask(String taskText);

    public abstract void editTask(String oldTaskText, String newTaskText);

    public abstract void startEditTask(String taskText);

    public abstract void typeInTaskInput(String text);

    public abstract void cancelEditTask();

    // Filtering
    public abstract void selectFilterByLabel(String label);

    public abstract void selectFilterByIndex(int index);

    // Verification methods
    public abstract boolean isTaskDisplayed(String taskText);

    public abstract int getRemainingTaskCount();

    public abstract String getRemainingTaskCountText();


}