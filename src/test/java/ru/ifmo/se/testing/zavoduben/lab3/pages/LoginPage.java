package ru.ifmo.se.testing.zavoduben.lab3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.Domain;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.Mailbox;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.User;
import ru.ifmo.se.testing.zavoduben.lab3.util.Constants;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public static LoginPage open(WebDriver driver) {
        driver.get(Constants.BASE_URL + "/login");
        return new LoginPage(driver);
    }

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
        // TODO: Check that this is the login page and throw IllegalStateException if it's not
    }

    private LoginPage typeUsername(String username) {
        switchToLoginFrame();

        WebElement loginField = driver.findElement(By.xpath("//input[@name='Login']"));
        loginField.clear();
        loginField.sendKeys(username);

        return this;
    }

    private void switchToLoginFrame() {
        driver.switchTo().defaultContent();
        // HACK: On e.mail.ru pages, login form is inside a frame with account.mail.ru
        // But if you get an error, you will get redirected to account.mail.ru.
        if (!driver.getCurrentUrl().contains("account.mail.ru")) {
            driver.switchTo().frame(0);
        }
    }

    private LoginPage clickDomainSelector() {
        switchToLoginFrame();

        By byXPath = By.xpath("//div[@class='domain-select']");
        WebElement domainSelector = driver.findElement(byXPath);
        domainSelector.click();

        return this;
    }

    private LoginPage selectDomainOptionFromSelector(String domain) {
        switchToLoginFrame();

        By byXPath = By.xpath("//div[contains(@class, 'Select-option') and .//*[contains(text(), '" + domain + "')]]");
        WebElement domainOption = driver.findElement(byXPath);
        domainOption.click();

        return this;
    }

    private LoginPage selectDomain(String domainName) {
        return this
                .clickDomainSelector()
                .selectDomainOptionFromSelector(domainName);
    }

    private LoginPage selectDomain(Domain domain) {
        return this.selectDomain(domain.getDomainName());
    }

    private LoginPage submitMailbox() {
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

        wait.until(visibilityOf(loginField));

        loginField.clear();
        loginField.sendKeys(password);

        return this;
    }

    public FolderPage submitPassword() {
        switchToLoginFrame();

        By byXPath = By.xpath("//button[@type='submit']");
        WebElement submitButton = driver.findElement(byXPath);
        submitButton.click();

        return new FolderPage(driver);
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

    public FolderPage loginAs(User user) {
        LoginPage loginPage = LoginPage.open(driver);

        loginPage.typeUsername(user.getUsername());
        loginPage.selectDomain(user.getDomain());
        loginPage.submitMailbox();

        loginPage.typePassword(user.getPassword());
        return loginPage.submitPassword();
    }

    public boolean isLoggedIn() {
        By byXPath = By.xpath("//*[@id='PH_authView']");
        WebElement headerForAuthenticatedUsers = driver.findElement(byXPath);
        return headerForAuthenticatedUsers.isDisplayed();
    }

    private LoginPage typeMailbox(Mailbox mailbox) {
        return this
                .typeUsername(mailbox.getName())
                .selectDomain(mailbox.getDomain());
    }

    public LoginPage typeAndSubmitMailbox(Mailbox mailbox) {
        return this
                .typeMailbox(mailbox)
                .submitMailbox();
    }
}
