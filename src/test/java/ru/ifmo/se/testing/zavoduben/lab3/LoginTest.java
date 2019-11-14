package ru.ifmo.se.testing.zavoduben.lab3;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.User;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.UserFixtureProvider;
import ru.ifmo.se.testing.zavoduben.lab3.pages.InboxPage;
import ru.ifmo.se.testing.zavoduben.lab3.pages.LoginPage;

import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LoginTest extends BaseTestConfiguration {

    private final WebDriver driver;

    public LoginTest(Supplier<WebDriver> driver, String driverName) {
        this.driver = driver.get();
    }

    @After
    public void quitBrowser() {
        driver.quit();
    }

    @Test
    public void loginPositive() {
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
    public void loginNegativeWrongPassword() {
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
    public void loginNegativeEmptyPassword() {
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
