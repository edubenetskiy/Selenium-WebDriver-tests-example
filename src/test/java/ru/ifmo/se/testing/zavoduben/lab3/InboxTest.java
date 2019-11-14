package ru.ifmo.se.testing.zavoduben.lab3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.User;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.UserFixtureProvider;
import ru.ifmo.se.testing.zavoduben.lab3.pages.InboxPage;
import ru.ifmo.se.testing.zavoduben.lab3.pages.LoginPage;

import java.util.function.Supplier;

import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class InboxTest extends BaseTestConfiguration {

    private final WebDriver driver;
    private InboxPage inboxPage;

    public InboxTest(Supplier<WebDriver> driverSupplier, String driverName) {
        this.driver = driverSupplier.get();
    }

    @Before
    public void logInAndOpenInbox() {
        User user = UserFixtureProvider.getInstance().getAnyUser();
        LoginPage loginPage = LoginPage.open(driver);
        this.inboxPage = loginPage.loginAs(user);
    }

    @Test
    public void logoutPositive() {
        LoginPage loginPage = inboxPage.logout();
        assertFalse(loginPage.isLoggedIn());
    }

    @After
    public void quitBrowser() {
        driver.quit();
    }
}
