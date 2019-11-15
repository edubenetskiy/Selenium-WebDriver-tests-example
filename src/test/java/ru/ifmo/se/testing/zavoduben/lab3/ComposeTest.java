package ru.ifmo.se.testing.zavoduben.lab3;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.User;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.UserFixtures;
import ru.ifmo.se.testing.zavoduben.lab3.pages.ComposePage;
import ru.ifmo.se.testing.zavoduben.lab3.pages.FolderPage;
import ru.ifmo.se.testing.zavoduben.lab3.pages.LoginPage;
import ru.ifmo.se.testing.zavoduben.lab3.util.WebDriverSupplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class ComposeTest extends BaseTestConfiguration {

    private final WebDriver driver;
    private FolderPage inboxPage;
    private ComposePage composePage;

    public ComposeTest(WebDriverSupplier driverSupplier) {
        this.driver = driverSupplier.get();
    }

    @Before
    public void logInAndOpenInbox() {
        User user = UserFixtures.getAnyUser();
        LoginPage loginPage = LoginPage.open(driver);
        this.inboxPage = loginPage.loginAs(user);
        this.composePage = inboxPage.compose();
    }

    @After
    public void quitBrowser() {
        driver.quit();
    }

    @Test
    public void cannotSendEmptyWithoutRecipient() {
        String errorMessage = composePage
                .sendExpectingError()
                .getErrorMessage();

        assertEquals("Не указан адрес получателя", errorMessage);
    }

    @Ignore
    @Test
    public void closingDoesNotSaveDraft() {
        fail();
    }

    @Ignore
    @Test
    public void discardingDoesNotSaveDraft() {
        fail();
    }

    @Ignore
    @Test
    public void savingComposedMessagePutsItToDraftBox() {
        fail();
    }

    @Ignore
    @Test
    public void sendingWithoutSubjectRaisesQuestion() {
        fail();
    }

    @Ignore
    @Test
    public void sendPositiveOneRecipient() {
        fail();
    }

    @Ignore
    @Test
    public void sendPositiveTwoRecipients() {
        fail();
    }

    @Ignore
    @Test
    public void sendPositiveRecipientAndCarbonCopy() {
        fail();
    }

    @Ignore
    @Test
    public void sendPositiveRecipientAndBlindCarbonCopy() {
        fail();
    }
}
