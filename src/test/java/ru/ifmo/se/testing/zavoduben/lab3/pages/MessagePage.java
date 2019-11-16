package ru.ifmo.se.testing.zavoduben.lab3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class MessagePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public MessagePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public boolean isMessagePage() {
        By byXPath = By.xpath("//div[contains(@class, 'letter__header')]");
        List<WebElement> messageHeader = driver.findElements(byXPath);
        return messageHeader.size() > 0;
    }

    public FolderPage markAsSpam() {
        By byXPath = By.xpath("//div[contains(@class, 'portal-menu-element_spam')]");
        WebElement markAsSpamButton = driver.findElement(byXPath);
        wait.until(elementToBeClickable(markAsSpamButton));
        markAsSpamButton.click();

        return FolderPage.assumeOpen(this.driver);
    }

    public FolderPage remove() {
        By byXPath = By.xpath("//div[contains(@class, 'portal-menu-element_remove')]");
        WebElement removeButton = driver.findElement(byXPath);
        wait.until(elementToBeClickable(removeButton));
        removeButton.click();

        return FolderPage.assumeOpen(this.driver);
    }

}
