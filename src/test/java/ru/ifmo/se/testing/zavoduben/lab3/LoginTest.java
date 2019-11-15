package ru.ifmo.se.testing.zavoduben.lab3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.Mailbox;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.User;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.UserFixtures;
import ru.ifmo.se.testing.zavoduben.lab3.pages.FolderPage;
import ru.ifmo.se.testing.zavoduben.lab3.pages.LoginPage;
import ru.ifmo.se.testing.zavoduben.lab3.util.WebDriverSupplier;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LoginTest extends BaseTestConfiguration {

    private final WebDriver driver;
    private LoginPage loginPage;

    public LoginTest(WebDriverSupplier driverSupplier) {
        this.driver = driverSupplier.get();
    }

    @Before
    public void openLoginPage() {
        loginPage = LoginPage.open(driver);
    }

    @After
    public void quitBrowser() {
        driver.quit();
    }

    @Test
    public void loginPositive() {
        User user = UserFixtures.getAnyUser();

        FolderPage inboxPage = loginPage.loginAs(user);
        assertEquals(user.getEmailAddress(), inboxPage.getCurrentUserEmail());
    }

    @Test
    public void loginNegativeNonExistingEmail() {
        Mailbox mailbox = UserFixtures.getNonExistingMailbox();

        String actualErrorMessage = loginPage
                .typeAndSubmitMailbox(mailbox)
                .getErrorMessage();

        String expectedMessage = "Такой аккаунт не зарегистрирован";
        assertEquals(expectedMessage, actualErrorMessage);
    }

    @Test
    public void loginNegativeWrongPassword() {
        User user = UserFixtures.getAnyUser();

        String actualErrorMessage = loginPage
                .typeAndSubmitMailbox(user.getMailbox())
                .typePassword("WrongPassword")
                .submitPasswordExpectingError()
                .getErrorMessage();

        String expected = "Неверный пароль, попробуйте ещё раз";
        assertEquals(expected, actualErrorMessage);
    }

    @Test
    public void loginNegativeEmptyPassword() {
        User user = UserFixtures.getAnyUser();

        String errorMessage = loginPage
                .typeAndSubmitMailbox(user.getMailbox())
                .typePassword("")
                .submitPasswordExpectingError()
                .getErrorMessage();

        String expected = "Поле «Пароль» должно быть заполнено";
        assertEquals(expected, errorMessage);
    }
}
