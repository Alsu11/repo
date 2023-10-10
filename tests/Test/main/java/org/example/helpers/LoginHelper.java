package org.example.helpers;

import org.example.ApplicationManager;
import org.example.models.Account;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

public class LoginHelper extends HelperBase {

    private Account account;

    public LoginHelper(ApplicationManager applicationManager) {
        super(applicationManager);
    }

    public void login(Account account) {
        if (isLogged()) {
            if (isLogged(account.getUserName())) {
                return;
            }
            logout();
        }
        this.account = account;
        driver.findElement(By.cssSelector(".login_btn")).click();
        driver.findElement(By.id("login_input1")).click();
        driver.findElement(By.id("login_input1")).sendKeys(account.getUserName());
        driver.findElement(By.id("login_input2")).click();
        driver.findElement(By.id("login_input2")).sendKeys(account.getPassword());
        driver.findElement(By.id("login_submit")).click();
    }
    public Boolean isLogged() {
        try {
            return driver.findElement(By.id("topLoginPanel")).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean isLogged(String userName) {
        driver.findElement(By.linkText("Профиль")).click();
        return driver.findElement(By.cssSelector(".user-title")).getText().equals(userName);
    }

    public void logout() {
        driver.get("https://jut.su/");
        driver.manage().window().setSize(new Dimension(550, 709));
        driver.findElement(By.linkText("[Выйти]")).click();
    }
}
