package ru.ifmo.se.testing.zavoduben.lab3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.User;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.UserFixtures;
import ru.ifmo.se.testing.zavoduben.lab3.pages.InboxPage;
import ru.ifmo.se.testing.zavoduben.lab3.pages.LoginPage;
import ru.ifmo.se.testing.zavoduben.lab3.util.WebDriverSupplier;

import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class ComposeTest extends BaseTestConfiguration {

    private final WebDriver driver;
    private InboxPage inboxPage;

    public ComposeTest(WebDriverSupplier driverSupplier) {
        this.driver = driverSupplier.get();
    }

    @Before
    public void logInAndOpenInbox() {
        User user = UserFixtures.getAnyUser();
        LoginPage loginPage = LoginPage.open(driver);
        this.inboxPage = loginPage.loginAs(user);
    }

    @After
    public void quitBrowser() {
        driver.quit();
    }

    @Test
    public void cannotSendEmptyWithoutRecipient() {
        fail();
    }

    @Test
    public void closingDoesNotSaveDraft() {
        fail();
    }

    @Test
    public void discardingDoesNotSaveDraft() {
        fail();
    }

    @Test
    public void savingComposedMessagePutsItToDraftBox() {
        fail();
    }

    @Test
    public void sendingWithoutSubjectRaisesQuestion() {
        fail();
    }

    @Test
    public void sendPositiveOneRecipient() {
        fail();
    }

    @Test
    public void sendPositiveTwoRecipients() {
        fail();
    }

    @Test
    public void sendPositiveRecipientAndCarbonCopy() {
        fail();
    }

    @Test
    public void sendPositiveRecipientAndBlindCarbonCopy() {
        fail();
    }
}
