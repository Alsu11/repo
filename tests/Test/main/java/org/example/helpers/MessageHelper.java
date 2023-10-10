package org.example.helpers;

import org.example.ApplicationManager;
import org.example.models.Message;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MessageHelper extends HelperBase {

    private Message message;

    public MessageHelper(ApplicationManager applicationManager) {
        super(applicationManager);
    }

    public void sendMessage(Message message) {
        driver.get("https://jut.su/pm/");
        driver.findElement(By.linkText("Отправить сообщение")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).sendKeys("PoshKamil");
        driver.findElement(By.cssSelector(".tableform > tbody > tr:nth-child(1) > td:nth-child(2)")).click();
        driver.findElement(By.name("subj")).click();
        driver.findElement(By.name("subj")).sendKeys("test");
        driver.findElement(By.id("comments")).click();
        driver.findElement(By.id("comments")).sendKeys(message.getTextMessage());
        driver.findElement(By.name("add")).click();
        driver.findElement(By.cssSelector(".main")).click();
        driver.findElement(By.linkText("вернуться на главную страницу")).click();
    }

    public void deleteMessage() {
        driver.get("https://jut.su/pm/");
        driver.findElement(By.name("selected_pm[]")).click();
        {
            WebElement element = driver.findElement(By.name("doaction"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).clickAndHold();
        }
        {
            WebElement element = driver.findElement(By.name("doaction"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).click();
        }
        {
            WebElement element = driver.findElement(By.name("doaction"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).release().click();
        }
        driver.findElement(By.name("doaction")).click();
        driver.findElement(By.cssSelector("option:nth-child(1)")).click();
        driver.findElement(By.cssSelector(".bbcodes:nth-child(2)")).click();
    }

    public Message getDataSentMessage() {
        driver.findElement(By.cssSelector(".login_panel_new_pm")).click();
        driver.findElement(By.cssSelector("tr:nth-child(1) b")).click();
        driver.findElement(By.cssSelector(".the_inpm_text")).click();
        String text = driver.findElement(By.cssSelector(".the_inpm_text")).getText();
        return new Message(text);
    }

    public Integer countMessages() {
        driver.get("https://jut.su/pm/");
        driver.findElement(By.name("selected_pm[]")).click();

        int i = 2;
        while (true) {
            try {
                driver.findElement(By.cssSelector("tr:nth-child(" + i + ") input")).isEnabled();
            } catch (NoSuchElementException e) {
                return i;
            }
            i++;
        }
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
