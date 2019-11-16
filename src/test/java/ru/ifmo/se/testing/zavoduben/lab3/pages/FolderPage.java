package ru.ifmo.se.testing.zavoduben.lab3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.User;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class FolderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private FolderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 10);
    }

    public static FolderPage open(Folder folder, WebDriver driver) {
        driver.get(folder.getUrl());
        return new FolderPage(driver);
    }

    public static FolderPage openInbox(WebDriver driver) {
        return open(Folder.INBOX, driver);
    }

    public static FolderPage openLoggedInAs(User user, WebDriver driver) {
        return LoginPage.open(driver).loginAs(user);
    }

    static FolderPage assumeOpen(WebDriver driver) {
        return new FolderPage(driver);
    }

    public String getCurrentUserEmail() {
        driver.switchTo().defaultContent();

        By byXPath = By.xpath("//*[@id='PH_user-email']");
        WebElement usernameLabel = driver.findElement(byXPath);

        ExpectedCondition<Boolean> emailIsDisplayed =
                textToBePresentInElement(usernameLabel, "@");
        wait.until(emailIsDisplayed);

        return usernameLabel.getText();
    }

    public LoginPage logout() {
        driver.switchTo().defaultContent();

        By byXPath = By.xpath("//*[@id='PH_logoutLink']");
        WebElement logoutBtn = driver.findElement(byXPath);
        wait.until(elementToBeClickable(logoutBtn));

        logoutBtn.click();

        return new LoginPage(driver);
    }

    public FolderPage removeAllMessages() {
        // TODO: Implement selecting and removing all messages
        return this;
    }

    public MessagePage getAnyMessage() {
        By byXPath = By.xpath("//div[contains(@class, 'js-letter-list-item')]");
        List<WebElement> messageItems = driver.findElements(byXPath);

        WebElement messageItem = messageItems.get(new Random().nextInt(messageItems.size()));
        wait.until(elementToBeClickable(messageItem));
        messageItem.click();

        return new MessagePage(this.driver);
    }

    public ComposePage compose() {
        driver.switchTo().defaultContent();

        By byXPath = By.xpath("//span[contains(" +
                              "concat(\" \",normalize-space(@class),\" \")," +
                              "\" compose-button \")" +
                              "]");

        WebElement composeButton = driver.findElement(byXPath);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='app-loader']")));
        wait.until(elementToBeClickable(composeButton));

        composeButton.click();

        return new ComposePage(driver);
    }

    public FolderPage goToFolder(Folder folder) {
        By byXPath = By.xpath("//a[contains(@href,'" + folder.getPath() + "')]");
        WebElement linkToFolder = driver.findElement(byXPath);
        wait.until(elementToBeClickable(linkToFolder));
        linkToFolder.click();

        ExpectedCondition<Boolean> folderIsLoaded = attributeContains(linkToFolder, "class", "nav__item_active");
        wait.until(folderIsLoaded);

        return FolderPage.assumeOpen(driver);
    }

    public List<Envelope> getEnvelopes() {
        List<WebElement> messageElements = driver.findElements(
                By.xpath(".//a[" +
                         "  contains(concat(' ',normalize-space(@class),' ')," +
                         "           ' llc ')" +
                         "]"));

        return messageElements.stream()
                .map(Envelope::from)
                .collect(Collectors.toList());
    }
}
