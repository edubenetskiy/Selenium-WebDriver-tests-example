package ru.ifmo.se.testing.zavoduben.lab3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ifmo.se.testing.zavoduben.lab3.util.Constants;

public class InboxPage {
    private final WebDriver driver;

    public InboxPage(WebDriver driver) {
        this.driver = driver;
    }

    public static InboxPage open(WebDriver driver) {
        driver.get(Constants.BASE_URL + "/messages/inbox");
        return new InboxPage(driver);
    }

    public String getCurrentUserEmail() {
        driver.switchTo().defaultContent();

        By byXPath = By.xpath("//*[@id='PH_user-email']");
        WebElement usernameLabel = driver.findElement(byXPath);

        Wait<WebDriver> wait = new WebDriverWait(driver, 10);
        ExpectedCondition<Boolean> emailIsDisplayed =
                ExpectedConditions.textToBePresentInElement(usernameLabel, "@");
        wait.until(emailIsDisplayed);

        return usernameLabel.getText();
    }

    public boolean isLoggedInAs(String username, String domain) {
        return getCurrentUserEmail().equals(username + "@" + domain);
    }

    public LoginPage logout() {
        driver.switchTo().defaultContent();

        By byXPath = By.xpath("//*[@id='PH_logoutLink']");
        WebElement logoutBtn = driver.findElement(byXPath);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(logoutBtn));

        logoutBtn.click();

        return new LoginPage(driver);
    }
}
