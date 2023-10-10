package org.example;

import org.example.helpers.LoginHelper;
import org.example.helpers.MessageHelper;
import org.example.helpers.NavigationHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;

public class ApplicationManager {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private LoginHelper loginHelper;
    private MessageHelper messageHelper;
    private NavigationHelper navigationHelper;
    private static ThreadLocal<ApplicationManager> app = new ThreadLocal<>();
    public ApplicationManager() {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        loginHelper = new LoginHelper(this);
        messageHelper = new MessageHelper(this);
        navigationHelper = new NavigationHelper(this);
    }

    protected void finalize() {
        try {
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ApplicationManager getInstance() {
        if(app.get() == null) {
            ApplicationManager applicationManager = new ApplicationManager();
            applicationManager.getNavigationHelper().openHomePage();
            app.set(applicationManager);
        }
        return app.get();
    }


    public LoginHelper getLoginHelper() {
        return loginHelper;
    }

    public MessageHelper getMessageHelper() {
        return messageHelper;
    }

    public NavigationHelper getNavigationHelper() {
        return navigationHelper;
    }

    public WebDriver getDriver() {
        return driver;
    }
}
