package ru.ifmo.se.testing.zavoduben.lab3;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.User;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.UserFixtureProvider;
import ru.ifmo.se.testing.zavoduben.lab3.pages.InboxPage;
import ru.ifmo.se.testing.zavoduben.lab3.pages.LoginPage;

import static org.testng.Assert.assertEquals;

public class LoginTest extends BaseTestConfiguration {

    private WebDriver driver;

    @BeforeClass
    public void retrieveDriver(ITestContext context) {
        driver = (WebDriver) context.getAttribute("driver");
    }

    @BeforeMethod
    public void logout() {
        try {
            InboxPage inboxPage = InboxPage.open(driver);
            inboxPage.logout();
        } catch (ElementNotInteractableException e) {
            // Don't mind.
        }
    }

    @Test
    void loginPositive() {
        LoginPage loginPage = LoginPage.open(driver);

        User user = UserFixtureProvider.getInstance().getAnyUser();

        loginPage.typeUsername(user.getUsername());
        loginPage.clickDomainSelector();
        loginPage.selectDomain(user.getDomain());
        loginPage.submitUsername();

        loginPage.typePassword(user.getPassword());
        InboxPage inboxPage = loginPage.submitPassword();

        assertEquals(user.getEmailAddress(), inboxPage.getCurrentUserEmail());
    }

    @Test
    void loginNegativeWrongPassword() {
        LoginPage loginPage = LoginPage.open(driver);

        User user = UserFixtureProvider.getInstance().getAnyUser();

        loginPage.typeUsername(user.getUsername());
        loginPage.clickDomainSelector();
        loginPage.selectDomain(user.getDomain());
        loginPage.submitUsername();

        loginPage.typePassword("WrongPassword");
        LoginPage loginPageAfterSubmit = loginPage.submitPasswordExpectingError();

        String expected = "Неверный пароль, попробуйте ещё раз";
        assertEquals(expected, loginPageAfterSubmit.getErrorMessage());
    }

    @Test
    void loginNegativeEmptyPassword() {
        LoginPage loginPage = LoginPage.open(driver);

        User user = UserFixtureProvider.getInstance().getAnyUser();

        loginPage.typeUsername(user.getUsername());
        loginPage.clickDomainSelector();
        loginPage.selectDomain(user.getDomain());
        loginPage.submitUsername();

        loginPage.typePassword("");
        LoginPage loginPageAfterSubmit = loginPage.submitPasswordExpectingError();

        String expected = "Поле «Пароль» должно быть заполнено";
        assertEquals(expected, loginPageAfterSubmit.getErrorMessage());
    }
}
