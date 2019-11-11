package ru.ifmo.se.testing.zavoduben.lab3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ru.ifmo.se.testing.zavoduben.lab3.util.Constants;

public class LoginPage {

    private final WebDriver driver;

    public static LoginPage open(WebDriver driver) {
        driver.get(Constants.BASE_URL + "/login");
        return new LoginPage(driver);
    }

    private LoginPage(WebDriver driver) {
        this.driver = driver;
        // TODO: Check that this is the login page and throw IllegalStateException if it's not
        PageFactory.initElements(driver, this);
    }

    public LoginPage typeUsername(String username) {
        switchToLoginFrame();

        WebElement loginField = driver.findElement(By.xpath("//input[@name='Login']"));
        loginField.clear();
        loginField.sendKeys(username);

        return this;
    }

    private void switchToLoginFrame() {
        driver.switchTo().defaultContent();
        // HACK: On e.mail.ru, login form is inside a frame, but on account.mail.ru, it is not
        if (!driver.getCurrentUrl().contains("account.mail.ru")) {
            driver.switchTo().frame(0);
        }
    }

    public LoginPage clickDomainSelector() {
        switchToLoginFrame();

        By byXPath = By.xpath("//div[@class='domain-select']");
        WebElement domainSelector = driver.findElement(byXPath);
        domainSelector.click();

        return this;
    }

    public LoginPage selectDomain(String domain) {
        switchToLoginFrame();

        By byXPath = By.xpath("//div[contains(@class, 'Select-option') and .//*[contains(text(), '" + domain + "')]]");
        WebElement domainOption = driver.findElement(byXPath);
        domainOption.click();

        return this;
    }

    public LoginPage submitUsername() {
        switchToLoginFrame();

        By byXPath = By.xpath("//button[@type='submit']");
        WebElement submitButton = driver.findElement(byXPath);
        submitButton.click();

        return this;
    }

    public LoginPage typePassword(String password) {
        switchToLoginFrame();

        By byXPath = By.xpath("//input[@name='Password']");
        WebElement loginField = driver.findElement(byXPath);
        loginField.clear();
        loginField.sendKeys(password);

        return this;
    }

    public InboxPage submitPassword() {
        switchToLoginFrame();

        By byXPath = By.xpath("//button[@type='submit']");
        WebElement submitButton = driver.findElement(byXPath);
        submitButton.click();

        return new InboxPage(driver);
    }

    public LoginPage submitPasswordExpectingError() {
        switchToLoginFrame();

        By byXPath = By.xpath("//button[@type='submit']");
        WebElement submitButton = driver.findElement(byXPath);
        submitButton.click();

        return new LoginPage(driver);
    }

    public String getErrorMessage() {
        switchToLoginFrame();

        By byXPath = By.xpath("//div[contains(@class, 'login-row')]//small");
        WebElement errorMessageLabel = driver.findElement(byXPath);

        return errorMessageLabel.getText();
    }
}
